/*
 * (c) Copyright 2008 Hewlett-Packard Development Company, LP
 * All rights reserved.
 * [See end of file]
 */

package com.hp.hpl.jena.tdb.store;

import com.hp.hpl.jena.graph.Graph;
import com.hp.hpl.jena.graph.Node;
import com.hp.hpl.jena.graph.Reifier;
import com.hp.hpl.jena.graph.Triple;
import com.hp.hpl.jena.graph.TripleMatch;
import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.shared.AlreadyReifiedException;
import com.hp.hpl.jena.shared.ReificationStyle;
import com.hp.hpl.jena.sparql.algebra.Algebra;
import com.hp.hpl.jena.sparql.algebra.Op;
import com.hp.hpl.jena.sparql.core.DataSourceGraphImpl;
import com.hp.hpl.jena.sparql.core.DatasetGraph;
import com.hp.hpl.jena.sparql.core.Var;
import com.hp.hpl.jena.sparql.engine.Plan;
import com.hp.hpl.jena.sparql.engine.QueryEngineFactory;
import com.hp.hpl.jena.sparql.engine.QueryEngineRegistry;
import com.hp.hpl.jena.sparql.engine.QueryIterator;
import com.hp.hpl.jena.sparql.engine.binding.Binding;
import com.hp.hpl.jena.sparql.engine.binding.BindingMap;
import com.hp.hpl.jena.util.iterator.ExtendedIterator;
import com.hp.hpl.jena.util.iterator.NiceIterator;
import com.hp.hpl.jena.util.iterator.NullIterator;
import com.hp.hpl.jena.vocabulary.RDF;

/** A Reifier that only support one style Standard (intercept, no conceal 
 *  -- and intercept is a no-op anyway because all triples 
 *  appear in the underlying graph for storing all triples). 
 */

public class TDBReifier implements Reifier
{
    private final static String qs = "PREFIX rdf: <"+RDF.getURI()+">\n" +
    		"SELECT * \n" +
    		"{ ?x rdf:type rdf:Statement ; rdf:subject ?S ; rdf:predicate ?P ; rdf:object ?O }" ;
    private final static Query query = QueryFactory.create(qs) ;
    private final static Op op = Algebra.compile(query) ; 
    private final static Var reifNodeVar = Var.alloc("x") ; 
    private final static Var varS = Var.alloc("S") ; 
    private final static Var varP = Var.alloc("P") ; 
    private final static Var varO = Var.alloc("O") ; 

    //private static ReificationStyle style = new ReificationStyle(false, false) ;
    private final Graph graph ;
    private final DatasetGraph ds  ;
    private final QueryEngineFactory factory ;

    public TDBReifier(Graph graph)
    {
        this.graph = graph ;
        this.ds = new DataSourceGraphImpl(graph) ;
        this.factory = QueryEngineRegistry.findFactory(op, ds, null) ;
    }
    
    @Override
    public ExtendedIterator allNodes()
    {
        // Or use graph.find( Node.ANY, RDF.Nodes.type, RDF.Nodes.Statement ) -> project subject
        return allNodes(null) ;
    }

    private static class MapperToNode extends NiceIterator
    {
        private final QueryIterator iter ;
        private Var var ;
        MapperToNode(QueryIterator iter, Var var) { this.iter = iter ; this.var = var ; }
        @Override public boolean hasNext() { return iter.hasNext() ; } 
        @Override public Node next()
        { 
            Binding b = iter.nextBinding();
            Node n = b.get(var) ;
            return n ;
        }
        @Override public void close() { iter.close() ; } 
    }

    @Override
    public ExtendedIterator allNodes(Triple triple)
    {
        QueryIterator qIter = nodesReifTriple(triple) ;
        return new MapperToNode(qIter, reifNodeVar) ;
    }
    
    private QueryIterator nodesReifTriple(TripleMatch t)
    {
        Binding b = null ;
        
        if ( t != null )
        {
            b = new BindingMap() ;
            b.add(Var.alloc("S"), t.getMatchSubject()) ;
            b.add(Var.alloc("P"), t.getMatchPredicate()) ;
            b.add(Var.alloc("O"), t.getMatchObject()) ;
        }
        
        Plan plan = factory.create(op, ds, b, null) ;
        QueryIterator qIter = plan.iterator() ;
        return qIter ;
    }
    
    @Override
    public void close()
    {}

    private static class MapperToTriple extends NiceIterator
    {
        private final QueryIterator iter ;
        MapperToTriple(QueryIterator iter) { this.iter = iter  ; }
        @Override public boolean hasNext() { return iter.hasNext() ; } 
        @Override public Triple next()
        { 
            Binding b = iter.nextBinding();
            Node S = b.get(varS) ;
            Node P = b.get(varP) ;
            Node O = b.get(varO) ;
            return new Triple(S,P,O) ;
        }
        @Override public void close() { iter.close() ; } 
    }
    
    @Override
    public ExtendedIterator find(TripleMatch match)
    {
        QueryIterator qIter = nodesReifTriple(match) ; 
        // To ExtendedIterator.
        return new MapperToTriple(qIter) ;
    }

    @Override
    public ExtendedIterator findEither(TripleMatch m, boolean showHidden)
    {
        return new NullIterator() ;
    }

    @Override
    public ExtendedIterator findExposed(TripleMatch m)
    {
        return new NullIterator() ;
    }

    @Override
    public Graph getParentGraph()
    {
        return graph ;
    }

    @Override
    public ReificationStyle getStyle()
    {
        return ReificationStyle.Standard ;
    }

    @Override
    public boolean handledAdd(Triple triple)
    {
        graph.add(triple) ;
        return true ;
    }

    @Override
    public boolean handledRemove(Triple triple)
    {
        graph.delete(triple) ;
        return true ;
    }

    @Override
    public boolean hasTriple(Node node)
    {
        if ( ! graph.contains(node, RDF.Nodes.type, RDF.Nodes.Statement))
            return false ;

        if ( ! graph.contains(node, RDF.Nodes.subject, Node.ANY) )
            return false ;
        if ( ! graph.contains(node, RDF.Nodes.predicate, Node.ANY) )
            return false ;
        if ( ! graph.contains(node, RDF.Nodes.object, Node.ANY) )
            return false ;
        return true ;
    }

    @Override
    public boolean hasTriple(Triple triple)
    {
        QueryIterator qIter = nodesReifTriple(triple) ; 
        boolean b = qIter.hasNext() ;
        qIter.close();
        return b ;
    }

    @Override
    public Node reifyAs(Node node, Triple triple)
    {
        if ( node == null )
            node = Node.createAnon() ;
        else
        {
            if ( hasTriple(node) )
                throw new AlreadyReifiedException(node) ;
        }
        
        graph.add(new Triple(node, RDF.Nodes.type, RDF.Nodes.Statement)) ;
        graph.add(new Triple(node, RDF.Nodes.subject, triple.getSubject())) ;
        graph.add(new Triple(node, RDF.Nodes.predicate, triple.getPredicate())) ;
        graph.add(new Triple(node, RDF.Nodes.object, triple.getObject())) ;

        return node ;
    }

    @Override
    public void remove(Triple triple)
    {
        remove(null, triple) ;
    }

    @Override
    public void remove(Node node, Triple triple)
    {
        if ( node == null )
            node = Node.ANY ;
        
        graph.getBulkUpdateHandler().remove(node, RDF.Nodes.type, RDF.Nodes.Statement) ;
        graph.getBulkUpdateHandler().remove(node, RDF.Nodes.subject, triple.getSubject()) ;
        graph.getBulkUpdateHandler().remove(node, RDF.Nodes.predicate, triple.getPredicate()) ;
        graph.getBulkUpdateHandler().remove(node, RDF.Nodes.object, triple.getObject()) ;
    }

    @Override
    public int size()
    {
        return -1 ;
    }

    @Override
    public Triple getTriple(Node node)
    {
        Node S = getNode(node, RDF.Nodes.subject) ;
        if ( S == null )
            return null ; 
        Node P = getNode(node, RDF.Nodes.predicate) ;
        if ( P == null )
            return null ; 
        Node O = getNode(node, RDF.Nodes.object) ;
        if ( O == null )
            return null ; 
        return new Triple(S,P,O) ;
    }

    private Node getNode(Node S, Node P)
    {
        ExtendedIterator it = graph.find(S,P, Node.ANY) ;
        if ( ! it.hasNext() ) return null ;
        Triple t = (Triple)it.next() ;
        it.close() ;
        return t.getObject() ;
    }
}

/*
 * (c) Copyright 2008 Hewlett-Packard Development Company, LP
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in the
 *    documentation and/or other materials provided with the distribution.
 * 3. The name of the author may not be used to endorse or promote products
 *    derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE AUTHOR ``AS IS'' AND ANY EXPRESS OR
 * IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
 * IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY DIRECT, INDIRECT,
 * INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT
 * NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
 * THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */