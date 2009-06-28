/*
 * (c) Copyright 2008, 2009 Hewlett-Packard Development Company, LP
 * All rights reserved.
 * [See end of file]
 */

package com.hp.hpl.jena.tdb.store;


import java.util.Iterator;

import org.slf4j.Logger;

import atlas.iterator.Iter;

import com.hp.hpl.jena.util.iterator.ExtendedIterator;
import com.hp.hpl.jena.util.iterator.NiceIterator;

import com.hp.hpl.jena.graph.*;
import com.hp.hpl.jena.graph.query.QueryHandler;

import com.hp.hpl.jena.shared.Lock;
import com.hp.hpl.jena.sparql.core.Quad;
import com.hp.hpl.jena.sparql.util.FmtUtils;

import com.hp.hpl.jena.tdb.TDB;
import com.hp.hpl.jena.tdb.base.file.Location;
import com.hp.hpl.jena.tdb.graph.*;
import com.hp.hpl.jena.tdb.solver.reorder.ReorderTransformation;
import com.hp.hpl.jena.tdb.sys.SystemTDB;

/** General operations for TDB graphs (free-standing graph, default graph and named graphs) */
public abstract class GraphTDBBase extends GraphBase2 implements GraphTDB
{
    private final QueryHandlerTDB queryHandler = new QueryHandlerTDB(this) ;
    private final TransactionHandler transactionHandler = new TransactionHandlerTDB(this) ;
    private final BulkUpdateHandler bulkUpdateHandler = new BulkUpdateHandlerTDB(this) ;
    private final ReorderTransformation reorderTransform  ;
    private final Location location ;
    protected final DatasetGraphTDB dataset ;
    protected final Node graphNode ;

    public GraphTDBBase(DatasetGraphTDB dataset, Node graphName, 
                        ReorderTransformation transformation, Location location)
    { 
        super() ;
        this.dataset = dataset ; 
        this.graphNode = graphName ;
        this.reorderTransform = transformation ;
        this.location = location ;
    }
    
    /** Reorder processor - may be null, for "none" */
    //@Override
    public final ReorderTransformation getReorderTransform()    { return reorderTransform ; }
    
    //@Override
    public final Location getLocation()                         { return location ; }
    
    //@Override
    public final Node getGraphNode()                            { return graphNode ; }
    
    //@Override
    public final DatasetGraphTDB getDataset()                   { return dataset ; }
    
    //@Override
    public Lock getLock()                                       { return dataset.getLock() ; }
    
    //@Override
    public abstract void sync(boolean force) ;
    
    protected void duplicate(Triple t)
    {
        if ( TDB.getContext().isTrue(SystemTDB.symLogDuplicates) && getLog().isInfoEnabled() )
        {
            String $ = FmtUtils.stringForTriple(t, this.getPrefixMapping()) ;
            getLog().info("Duplicate: ("+$+")") ;
        }
    }

    
    protected static ExtendedIterator<Triple> graphBaseFindWorker(TripleTable tripleTable, TripleMatch m)
    {
        // See also SolverLib.execute
        Iterator<Triple> iter = tripleTable.find(m.getMatchSubject(), m.getMatchPredicate(), m.getMatchObject()) ;
        if ( iter == null )
            return com.hp.hpl.jena.util.iterator.NullIterator.instance() ;
        return new MapperIteratorTriples(iter) ;
    }
    

    
    @Override
    protected Reifier constructReifier()
    {
        return new Reifier2(this) ;
    }
    
    protected abstract Logger getLog() ;
    
    /** Iterator over something that, when counted, is the graph size. */
    protected abstract Iterator<?> countThis() ;

    @Override
    protected final int graphBaseSize()
    {
        Iterator<?> iter = countThis() ;
        return (int)Iter.count(iter) ;
    }
    
    // Convert from Iterator<Triple> to ExtendedIterator
    static class MapperIteratorTriples extends NiceIterator<Triple>
    {
        private final Iterator<Triple> iter ;
        MapperIteratorTriples(Iterator<Triple> iter) { this.iter = iter ; }
        @Override public boolean hasNext() { return iter.hasNext() ; } 
        @Override public Triple next() { return iter.next(); }
        @Override public void remove() { iter.remove(); }
    }
    
    // Convert from Iterator<Quad> to Iterator<Triple>
    static class ProjectQuadsToTriples implements Iterator<Triple>
    {
        private final Iterator<Quad> iter ;
        private final Node graphNode ;
        /** Project quads to triples - check the graphNode is as expected if not null */
        ProjectQuadsToTriples(Node graphNode, Iterator<Quad> iter) { this.graphNode = graphNode ; this.iter = iter ; }
        //@Override
        public boolean hasNext() { return iter.hasNext() ; }
        
        //@Override
        public Triple next()
        { 
            Quad q = iter.next();
            if ( graphNode != null && ! q.getGraph().equals(graphNode))
                throw new InternalError("ProjectQuadsToTriples: Quads from unexpected graph") ;
            return q.getTriple() ;
        }
        //@Override
        public void remove() { iter.remove(); }
    }
    
    @Override
    public BulkUpdateHandler getBulkUpdateHandler() {return bulkUpdateHandler ; }

    @Override
    public Capabilities getCapabilities()
    {
        if ( capabilities == null )
            capabilities = new Capabilities(){
                public boolean sizeAccurate() { return true; }
                public boolean addAllowed() { return true ; }
                public boolean addAllowed( boolean every ) { return true; } 
                public boolean deleteAllowed() { return true ; }
                public boolean deleteAllowed( boolean every ) { return true; } 
                public boolean canBeEmpty() { return true; }
                public boolean iteratorRemoveAllowed() { return false; } /* ** */
                public boolean findContractSafe() { return true; }
                public boolean handlesLiteralTyping() { return false; } /* ** */
            } ; 
        
        return super.getCapabilities() ;
    }
    
    @Override
    public QueryHandler queryHandler()
    { return queryHandler ; }
    
    @Override
    public TransactionHandler getTransactionHandler()
    { return transactionHandler ; }
    
//    protected GraphStatisticsHandler createStatisticsHandler()
//    { return null; }
 
}

/*
 * (c) Copyright 2008, 2009 Hewlett-Packard Development Company, LP
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