/*
 * (c) Copyright 2008, 2009 Hewlett-Packard Development Company, LP
 * All rights reserved.
 * [See end of file]
 */

package com.hp.hpl.jena.tdb.index.ext;

//import static ext.ExtHashTestBase.* ; 
import org.junit.AfterClass;
import org.junit.BeforeClass;

import com.hp.hpl.jena.tdb.base.block.BlockMgr;
import com.hp.hpl.jena.tdb.base.block.BlockMgrFactory;
import com.hp.hpl.jena.tdb.base.file.PlainFileMem;
import com.hp.hpl.jena.tdb.base.record.RecordFactory;
import com.hp.hpl.jena.tdb.index.Index;
import com.hp.hpl.jena.tdb.index.TestIndex;
import com.hp.hpl.jena.tdb.index.ext.ExtHash;
import com.hp.hpl.jena.tdb.sys.SystemTDB;

public class TestExtHash extends TestIndex
{
    @BeforeClass static public void setup()
    {
        SystemTDB.NullOut = true ;
        ExtHash.Checking = true ;
        ExtHash.Logging = false ;
    }
    
    @AfterClass static public void teardown()
    {
        
    }

    @Override
    protected Index makeIndex()
    {
        RecordFactory factory = new RecordFactory(4, 0) ;
        BlockMgr mgr = BlockMgrFactory.createMem("EXT", 32) ;
        ExtHash eHash = new ExtHash(new PlainFileMem(), factory, mgr) ;
        return eHash ;
    }
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