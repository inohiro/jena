package dev.pldms;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.hp.hpl.jena.graph.Node;
import com.hp.hpl.jena.sdb.SDBFactory;
import com.hp.hpl.jena.sdb.layout2.Layout2TupleLoaderBase;
import com.hp.hpl.jena.sdb.layout2.hash.StoreTriplesNodesHashHSQL;
import com.hp.hpl.jena.sdb.layout2.hash.TupleLoaderHashHSQL;
import com.hp.hpl.jena.sdb.sql.JDBC;
import com.hp.hpl.jena.sdb.sql.SDBConnection;
import com.hp.hpl.jena.sdb.store.Store;
import com.hp.hpl.jena.sdb.store.TableDesc;


public class Scratch {

        /**
         * @param args
         * @throws SQLException 
         */
        public static void main(String[] args) throws SQLException {
                
                JDBC.loadDriverHSQL();

                SDBConnection sdb = SDBFactory.createConnection(
                                "jdbc:hsqldb:mem:aname", "sa", "");

                Store store = new StoreTriplesNodesHashHSQL(sdb);

                store.getTableFormatter().format();
                TableDesc desc = store.getTripleTableDesc();
                
                Layout2TupleLoaderBase tl = new TupleLoaderHashHSQL(sdb, desc, 50);
                
                System.out.println("-------------");
                System.out.println(tl.getCreateTempNodes());
                System.out.println("-------------");
                System.out.println(tl.getLoadTuples());
                System.out.println("-------------");
                System.out.println(tl.getDeleteTuples());
                System.out.println("-------------");
                System.out.println(tl.getInsertTempNodes());
                System.out.println("-------------");
                System.out.println(tl.getInsertTempTuples());
                System.out.println("-------------");
                Node[] s1 = new Node[] {Node.createAnon(), Node.createAnon(), Node.createAnon()};
                Node[] s2 = new Node[] {Node.createAnon(), Node.createAnon(), Node.createAnon()};
                tl.load(s1);
                tl.finish();
                tl.load(s2);
                tl.finish();
                System.out.println("Node count: " + getSize("Nodes", sdb));
                System.out.println("Tuple count: " + getSize(desc.getTableName(), sdb));
                tl.unload(s1);
                tl.unload(s2);
                tl.finish();
                System.out.println("Node count: " + getSize("Nodes", sdb));
                System.out.println("Tuple count: " + getSize(desc.getTableName(), sdb));
                
                store.close();
        }
        
        public static Integer getSize(String table, SDBConnection conn) {
        	Integer size = -1;
        	
        	try {
        		ResultSet result = conn.execQuery("SELECT COUNT(*) AS NUM FROM " + table);
        		
        		if (result.next()) {
        			size = result.getInt("NUM");
        		}
				result.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
        	
        	return size;
        }
}