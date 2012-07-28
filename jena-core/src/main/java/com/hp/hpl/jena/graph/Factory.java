/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.hp.hpl.jena.graph;

import com.hp.hpl.jena.mem.impl.GraphMem ;
import com.hp.hpl.jena.shared.*;

/**
    A factory class for creating Graphs.

*/

public class Factory
    {
    private Factory()
        { super(); }
    
    /**
        Answer a memory-based Graph with the Standard reification style.
    */
    public static Graph createDefaultGraph()
        { return createDefaultGraph( ReificationStyle.Standard ); }
        
    /**
        Answer a memory-based Graph with the given reification style.
    */
    public static Graph createDefaultGraph( ReificationStyle style )
        { return Factory.createGraphMem( style ); }
              
    public static Graph createGraphMem()
        { return new GraphMem(); }

    public static Graph createGraphMem( ReificationStyle style )
        { return new GraphMem( style ); }

    public static Graph createGraphMemWithTransactionHandler( final TransactionHandler th )
        {
        Graph g = new GraphMem() 
            {
            @Override
            public TransactionHandler getTransactionHandler() 
                {  return th; }
            };
        return g;
        }
    }
