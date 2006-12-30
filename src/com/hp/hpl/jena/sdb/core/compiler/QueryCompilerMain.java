/*
 * (c) Copyright 2006 Hewlett-Packard Development Company, LP
 * All rights reserved.
 * [See end of file]
 */

package com.hp.hpl.jena.sdb.core.compiler;

import java.util.List;

import com.hp.hpl.jena.query.core.Var;
import com.hp.hpl.jena.query.engine2.op.*;

import com.hp.hpl.jena.sdb.core.SDBRequest;
import com.hp.hpl.jena.sdb.core.sqlnode.SqlNode;
import com.hp.hpl.jena.sdb.store.SQLBridge;


public abstract class QueryCompilerMain implements QueryCompiler 
{
    // Do we need this as a class?
    private List<Var> projectVars ;
    protected SDBRequest request ;
    
    public QueryCompilerMain(SDBRequest request)
    { 
        this.request = request ;
        projectVars = QC.projectVars(request.getQuery()) ;
    }
    
    public Op compile(Op op)
    {
        Transform t = new TransformSDB(request, createQuadBlockCompiler()) ;
        op = Transformer.transform(t, op) ;
        OpWalker.walk(op, new SqlNodesFinisher()) ;
        return op ;
    }

    protected abstract QuadBlockCompiler createQuadBlockCompiler() ;
    
    public ConditionCompiler getConditionCompiler()
    {
        return null ;
    }
    
    private class SqlNodesFinisher extends OpVisitorBase
    {
        @Override
        public void visit(OpExt op)
        {
            if ( ! ( op instanceof OpSQL ) )
            {
                super.visit(op) ;
                return ;
            }
            OpSQL opSQL = (OpSQL)op ;
            
            SqlNode sqlNode = opSQL.getSqlNode() ;
            
            SQLBridge bridge =  request.getStore().getSQLBridgeFactory().create(request) ;
            sqlNode = QC.toSqlTopNode(sqlNode, projectVars, bridge) ;
            opSQL.setBridge(bridge) ;
            opSQL.resetSqlNode(sqlNode) ;
            // Insert value stuff.  Change opSQL
            // Do the project bridge.
        }
    }
}

/*
 * (c) Copyright 2006 Hewlett-Packard Development Company, LP
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