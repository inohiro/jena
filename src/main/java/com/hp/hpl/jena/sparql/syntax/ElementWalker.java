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

package com.hp.hpl.jena.sparql.syntax;


/** An element visitor that walks the graph pattern tree, 
 *  applying a visitor at each Element traversed.
 *  Does not (NOT)EXISTS in filters.
 *    These will need to call down themselves if it is meaningful for the visitor.  
 *  Bottom-up walk - apply to subelements before applying to current element.
 */

public class ElementWalker 
{
    // See also RecursiveElementVisitor
    
    public static void walk(Element el, ElementVisitor visitor)
    {
        walk(el, new Walker(visitor)) ;
    }

    public static void walk(Element el, Walker walker)
    {
        el.visit(walker) ;
    }

    
//    public void walk(Element el)
//    {
//        el.visit(new Walker(proc)) ;
//    }
    
    static public class Walker implements ElementVisitor
    {
        protected ElementVisitor proc ;
        protected Walker(ElementVisitor visitor)
        { 
            proc = visitor ;
        }
        
        @Override
        public void visit(ElementTriplesBlock el)
        {
            proc.visit(el) ;
        }
        
        @Override
        public void visit(ElementFilter el)
        {
            proc.visit(el) ;
        }

        @Override
        public void visit(ElementAssign el)
        {
            proc.visit(el) ;
        }
        
        @Override
        public void visit(ElementBind el)
        {
            proc.visit(el) ;
        }
        
        @Override
        public void visit(ElementData el)
        {
            proc.visit(el) ;
        }
        
        @Override
        public void visit(ElementUnion el)
        {
            for ( Element e : el.getElements() )
                e.visit(this) ;
            proc.visit(el) ;
        }
        
        @Override
        public void visit(ElementGroup el)
        {
            for (Element e : el.getElements())
                e.visit(this) ;
            proc.visit(el) ;
        }
    
        @Override
        public void visit(ElementOptional el)
        {
            if ( el.getOptionalElement() != null )
                el.getOptionalElement().visit(this) ;
            proc.visit(el) ;
        }
        
        @Override
        public void visit(ElementDataset el)
        {
            if ( el.getPatternElement() != null )
                el.getPatternElement().visit(this) ;
            proc.visit(el) ;
        }

        @Override
        public void visit(ElementNamedGraph el)
        {
            if ( el.getElement() != null )
                el.getElement().visit(this) ;
            proc.visit(el) ;
        }
    
        @Override
        public void visit(ElementService el)
        {
            if ( el.getElement() != null )
                el.getElement().visit(this) ;
            proc.visit(el) ;
        }

        @Override
        public void visit(ElementFetch el)
        {
            proc.visit(el) ;
        }

        // EXISTs, NOT EXISTs also occur in FILTERs via expressions.
        
        @Override
        public void visit(ElementExists el)
        {
            proc.visit(el) ;
        }

        @Override
        public void visit(ElementNotExists el)
        {
            proc.visit(el) ;
        }

        @Override
        public void visit(ElementMinus el)
        {
            if ( el.getMinusElement() != null )
                el.getMinusElement().visit(this) ;
            proc.visit(el) ;
        }
        
        @Override
        public void visit(ElementSubQuery el)
        {
            proc.visit(el) ;
        }

        @Override
        public void visit(ElementPathBlock el)
        {
            proc.visit(el) ;
        }
    }
}
