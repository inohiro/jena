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

package com.hp.hpl.jena.graph.query;

/**
	ExpressionFunctionURIs: constants expressing the URIs for functions that
    may be recognised or generated by expression constructors and analysers.

*/
public interface ExpressionFunctionURIs 
    {
    public static final String prefix = "urn:x-jena:expr:";
    
    /**
         Operator used to AND conditions together. The Query.addConstraint()
         method explodes ANDed expressions into their components and keeps
         them separately.
    */
    
    public static final String AND = prefix + "AND";
    
    /**
         Function identfier for "L endsWith string literal R", generated by Rewrite.
    */
    public static final String J_EndsWith = prefix + "J_endsWith";
    
    /**
         Function identfier for "L startsWith string literal R", generated by Rewrite.
    */    
    public static final String J_startsWith = prefix + "J_startsWith";
    
    public static final String J_startsWithInsensitive = prefix + "J_startsWithInsensitive";
    
    public static final String J_endsWithInsensitive = prefix + "J_endsWithInsensitive";
    
    /**
         Function identfier for "L contains string literal R", generated by Rewrite.
    */
    public static final String J_contains = prefix + "J_contains";
    
    public static final String J_containsInsensitive = prefix + "J_containsInsensitive";

    /**
         Function identifier for RDQL-style string-match operation. This is recognised
         by Query and rewritten by Rewrite to the J_* methods. The left operand
         may be any expression, but the right operand must be a PatternLiteral.
    */
    public static final String Q_StringMatch = prefix + "Q_StringMatch"; 
    
    }
