/*
 * (c) Copyright 2010 Talis Systems Ltd.
 * All rights reserved.
 * [See end of file]
 */

package org.openjena.riot.inf;

import org.openjena.atlas.lib.Sink ;

import com.hp.hpl.jena.graph.Triple ;
import com.hp.hpl.jena.rdf.model.Model ;
import com.hp.hpl.jena.sparql.core.Quad ;

public class InfFactory
{
    public static Sink<Triple> infTriples(Sink<Triple> sink, Model vocab)
    {
        InferenceSetupRDFS setup =  new InferenceSetupRDFS(vocab) ;
        return new InferenceProcessorTriples(sink, setup) ; 
    }
    
    public static Sink<Quad> infQuads(Sink<Quad> sink, Model vocab)
    {
        InferenceSetupRDFS setup =  new InferenceSetupRDFS(vocab) ;
        return new InferenceProcessorQuads(sink, setup) ; 
    }

    public static Sink<Triple> infTriples(Sink<Triple> sink, InferenceSetupRDFS setup)
    {
        return new InferenceProcessorTriples(sink, setup) ; 
    }
    
    public static Sink<Quad> infQuads(Sink<Quad> sink, InferenceSetupRDFS setup)
    {
        return new InferenceProcessorQuads(sink, setup) ; 
    }
}

/*
 * (c) Copyright 2010 Talis Systems Ltd.
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