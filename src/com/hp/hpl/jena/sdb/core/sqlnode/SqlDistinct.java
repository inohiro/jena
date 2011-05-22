/*
 * (c) Copyright 2007, 2008, 2009 Hewlett-Packard Development Company, LP
 * All rights reserved.
 * [See end of file]
 */

package com.hp.hpl.jena.sdb.core.sqlnode;

import com.hp.hpl.jena.sdb.shared.SDBInternalError;

// Not used - may be removed
/*public*/ class SqlDistinct extends SqlNodeBase1
{
    private static SqlNode distinct(SqlNode sqlNode)
    {
        return null ;
        //return SqlSelectBlock.distinct(sqlNode) ;
        
//        SqlDistinct p = null ;
//        if ( sqlNode.isDistinct() )
//            p = sqlNode.asDistinct() ;
//        else
//            p = new SqlDistinct(sqlNode) ;
//        return p ;
    }
    
    private SqlDistinct(SqlNode sqlNode)
    {
        super(null, sqlNode) ;
    }
    
    @Override
    public boolean isDistinct() { return true ; }
    @Override
    public SqlDistinct asDistinct() { return this ; }
    
    //@Override
    public void visit(SqlNodeVisitor visitor)
    { throw new SDBInternalError("SqlDistinct.visit") ; /*visitor.visit(this) ;*/ }
    
    @Override
    public SqlNode apply(SqlTransform transform, SqlNode subNode)
    { throw new SDBInternalError("SqlDistinct.transform") ; /* return transform.transform(this, subNode) ; */ }

    @Override
    public SqlNode copy(SqlNode subNode)
    {
        return new SqlDistinct(subNode) ;
    }
}

/*
 * (c) Copyright 2007, 2008, 2009 Hewlett-Packard Development Company, LP
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