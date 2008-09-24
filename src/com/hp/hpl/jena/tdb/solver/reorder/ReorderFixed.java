/*
 * (c) Copyright 2008 Hewlett-Packard Development Company, LP
 * All rights reserved.
 * [See end of file]
 */

package com.hp.hpl.jena.tdb.solver.reorder;

import static com.hp.hpl.jena.tdb.solver.reorder.PatternElements.TERM;
import static com.hp.hpl.jena.tdb.solver.reorder.PatternElements.VAR;

import com.hp.hpl.jena.sparql.sse.Item;
import com.hp.hpl.jena.tdb.lib.NodeConst;
import com.hp.hpl.jena.tdb.solver.stats.StatsMatcher;
import com.hp.hpl.jena.tdb.solver.stats.StatsMatcher.Pattern;

public class ReorderFixed extends ReorderTransformationBase
{
    // Fixed scheme for when we have no stats.
    // It chooses a triple pattern by order of preference.
    
    private static Item type = Item.createNode(NodeConst.nodeRDFType) ;
    
    static StatsMatcher matcher ;
    static {
        matcher = new StatsMatcher() ;
        
        //matcher.addPattern(new Pattern(1,   TERM, TERM, TERM)) ;     // SPO - built-in - not needed a s a rule
        
        matcher.addPattern(new Pattern(2,   TERM, TERM, VAR)) ;     // SP?
        matcher.addPattern(new Pattern(5,   TERM, type, TERM)) ;    // ? type O -- worse than ?PO
        matcher.addPattern(new Pattern(3,   VAR,  TERM, TERM)) ;    // ?PO
        matcher.addPattern(new Pattern(2,   TERM, TERM, TERM)) ;    // S?O
        
        matcher.addPattern(new Pattern(10,  TERM, VAR,  VAR)) ;     // S??
        matcher.addPattern(new Pattern(20,  VAR,  VAR,  TERM)) ;    // ??O
        matcher.addPattern(new Pattern(30,  VAR,  TERM, VAR)) ;     // ?P?

        matcher.addPattern(new Pattern(100, VAR,  VAR,  VAR)) ;     // ???
    }
    
    @Override
    protected double weight(PatternTriple pt)
    {
        return matcher.match(pt) ;
        
//        int count = 0 ;
//        // Var.isVar is null-safe
//        if ( Var.isVar(pt.subject.getNode()) )
//            count++ ;
//        if ( Var.isVar(pt.predicate.getNode()) )
//            count++ ;
//        if ( Var.isVar(pt.object.getNode()) )
//            count++ ;
//        else
//        {
//            // ?x rdf:type <TYPE>
//            if ( rdfType.equals(pt.predicate.getNode()) )
//                // Discourage rdf:type.
//                count += 0.5 ;
//        }   
//        return count ;
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