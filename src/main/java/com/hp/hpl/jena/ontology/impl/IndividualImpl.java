/*****************************************************************************
 * Source code information
 * -----------------------
 * Original author    Ian Dickinson, HP Labs Bristol
 * Author email       ian_dickinson@users.sourceforge.net
 * Package            Jena 2
 * Web                http://sourceforge.net/projects/jena/
 * Created            31-Mar-2003
 * Filename           $RCSfile: IndividualImpl.java,v $
 * Revision           $Revision: 1.2 $
 * Release status     $State: Exp $
 *
 * Last modified on   $Date: 2009-10-06 13:04:42 $
 *               by   $Author: ian_dickinson $
 *
 * (c) Copyright 2002, 2003, 2004, 2005, 2006, 2007, 2008, 2009 Hewlett-Packard Development Company, LP
 * (see footer for full conditions)
 *****************************************************************************/

// Package
///////////////
package com.hp.hpl.jena.ontology.impl;


// Imports
///////////////
import com.hp.hpl.jena.ontology.*;
import com.hp.hpl.jena.rdf.model.*;
import com.hp.hpl.jena.util.iterator.ExtendedIterator;
import com.hp.hpl.jena.enhanced.*;
import com.hp.hpl.jena.graph.*;


/**
 * <p>
 * Implementation for the ontology abstraction representing ontology class descriptions.
 * </p>
 *
 * @author Ian Dickinson, HP Labs
 *         (<a  href="mailto:ian_dickinson@users.sourceforge.net" >email</a>)
 * @version CVS $Id: IndividualImpl.java,v 1.2 2009-10-06 13:04:42 ian_dickinson Exp $
 */
public class IndividualImpl
    extends OntResourceImpl
    implements Individual
{
    // Constants
    //////////////////////////////////

    // Static variables
    //////////////////////////////////

    /**
     * A factory for generating Individual facets from nodes in enhanced graphs.
     * Note: should not be invoked directly by user code: use
     * {@link com.hp.hpl.jena.rdf.model.RDFNode#as as()} instead.
     */
    @SuppressWarnings("hiding")
    public static Implementation factory = new Implementation() {
        @Override
        public EnhNode wrap( Node n, EnhGraph eg ) {
            if (canWrap( n, eg )) {
                return new IndividualImpl( n, eg );
            }
            else {
                throw new ConversionException( "Cannot convert node " + n.toString() + " to Individual");
            }
        }

        @Override
        public boolean canWrap( Node node, EnhGraph eg ) {
            // node will support being an Individual facet if it is a URI node or bNode
            Profile profile = (eg instanceof OntModel) ? ((OntModel) eg).getProfile() : null;
            return (profile != null)  &&  profile.isSupported( node, eg, Individual.class );
        }
    };





    // Instance variables
    //////////////////////////////////

    // Constructors
    //////////////////////////////////

    /**
     * <p>
     * Construct an individual represented by the given node in the given graph.
     * </p>
     *
     * @param n The node that represents the resource
     * @param g The enh graph that contains n
     */
    public IndividualImpl( Node n, EnhGraph g ) {
        super( n, g );
    }


    // External signature methods
    //////////////////////////////////


    /**
     * <p>Set the ontology class for this individual, replacing any
     * existing class membership. Class membership is encoded using the
     * <code>rdf:type</code> property. Any existing statements for the RDF type
     * will first be removed.</p>
     *
     * @param cls The RDF resource denoting the new class to which this individual belongs,
     *                 which will replace any existing <code>rdf:type</code> property.
     */
    public void setOntClass( Resource cls ) {
        setRDFType( cls );
    }

    /**
     * <p>Add the given ontology class as one of the classes to which
     * this individual belongs. Class membership is encoded using the
     * <code>rdf:type</code> property. </p>
     *
     * @param cls An RDF resource denoting an additional class to which this individual
     * belongs.
     */
    public void addOntClass( Resource cls ) {
        addRDFType( cls );
    }

    /**
     * <p>
     * Answer an ontology class to which this individual belongs. If the individual
     * belongs to more than one class, which is common in ontology models using
     * a reasoner, then the return value will be one of
     * the possible values but <strong>it is not specified which one</strong>.
     * In the case of multiple classes, callers <strong>should not</strong> rely on
     * the return value being consistent, e.g. across runs, since it may
     * depend on the underlying hash indexes in the model. </p>
     * <p>This method considers any ontology class for the individual, not just
     * <em>direct</em> classes. It is equivalent to <code>getOntClass(false)</code>.
     * </p>
     *
     * @return A resource denoting the ontology class for this individual, or one of them if
     * more than one is defined.
     * @exception ConversionException if the return value is known to be an
     * ontology class, assuming strict type checking is turned on for the underlying
     * <code>OntModel</code>. See {@link OntModel#setStrictMode(boolean)}
     */
    public OntClass getOntClass() {
        return getOntClass( false );
    }

    /**
     * <p>
     * Answer an ontology class to which this individual belongs. If the resource
     * belongs to more than one class, which is common in ontology models using
     * a reasoner, then the return value will be one of
     * the possible values but <strong>it is not specified which one</strong>.
     * In the case of multiple classes, callers <strong>should not</strong> rely on
     * the return value being consistent, e.g. across runs, since it may
     * depend on the underlying hash indexes in the model. </p>
     *
     * @param direct If <code>true</code>, only <em>direct</em> classes are considered.
     * A class is a direct class of this <code>Individual</code> if and only if
     * there is no other resource is both an <code>rdf:type</code> of this
     * individual and a sub-class of the candidate class.
     *
     * @return A resource denoting the ontology class for this individual, or one of them if
     * more than one is defined.
     * @exception ConversionException if the return value is known to be an
     * ontology class, assuming strict type checking is turned on for the underlying
     * <code>OntModel</code>. See {@link OntModel#setStrictMode(boolean)}
     */
    public OntClass getOntClass( boolean direct ) {
        return (getRDFType( direct ).as( OntClass.class ));
    }

    /**
     * <p>
     * Answer an iterator over the ontology classes to which this individual belongs.
     * The members of the iterator will be {@link OntClass} objects.
     * </p>
     *
     * @param direct If true, only answer those resources that are direct types
     * of this individual, not the super-classes of the class etc.
     * @return An iterator over the set of this individual's classes. Each member
     * of the iteration will be an {@link OntClass}.
     */
    public <T extends OntClass> ExtendedIterator<T> listOntClasses( boolean direct ) {
        @SuppressWarnings("unchecked")
        ExtendedIterator<T> iter = 
            (ExtendedIterator<T>)listRDFTypes( direct ).mapWith( new ResourceAsMapper<OntClass>( OntClass.class ) );
        return iter ;
    }

    /**
     * <p>
     * Answer true if this individual is a member of the class denoted by the
     * given class resource.
     * </p>
     *
     * @param ontClass Denotes an ontology class to which this individual may belong
     * @param direct If true, only consider the direct types of this individual, ignoring
     * the super-classes of the stated types.
     * @return True if this individual is a member of the given class, possibly taking the
     * directness constraint into account.
     */
    public boolean hasOntClass( Resource ontClass, boolean direct ) {
        return hasRDFType( ontClass, direct );
    }

    /**
     * <p>
     * Answer true if this individual is a member of the class denoted by the
     * given ontology class resource.  Not limited to only direct class relationships,
     * so this is equivalent to:
     * <code><pre>
     * hasOntClass( ontClass, false );
     * </pre></code>
     * </p>
     *
     * @param ontClass Denotes a class to which this individual may belong
     * @return True if this individual has the given class as one of its <code>rdf:type</code>'s.
     */
    public boolean hasOntClass( Resource ontClass ) {
        return hasOntClass( ontClass, false );
    }

    /**
     * <p>
     * Answer true if this individual is a member of the class denoted by the
     * given URI.</p>
     *
     * @param uri Denotes the URI of a class to which this value may belong
     * @return True if this individual has the given class as one of its <code>rdf:type</code>'s.
     */
    public boolean hasOntClass( String uri ) {
        return hasRDFType( uri );
    }

    /**
     * <p>Attempt to remove this <code>individual</code> as a member of the
     * given ontology class. This relationship is represented by a <code>rdf:type</code>
     * statement in the underlying model. If this relationship was originally
     * asserted, then removal will always succeed. However, if the <code>rdf:type</code>
     * relationship is entailed by the action of an attached reasoner, it may not be
     * possible to directly remove it. Callers should instead update the assertions
     * and axioms that entail the class membership relationship, and ensure the
     * reasoner gets chance to update the entailments.</p>
     * <p>If this individual is not a member of the given class, the
     * operation has no effect.</p>
     *
     * @param ontClass A resource denoting a class that that is to be removed from
     * the set of classes to which this individual belongs
     */
    public void removeOntClass( Resource ontClass ) {
        removeRDFType( ontClass );
    }



    // Internal implementation methods
    //////////////////////////////////

    //==============================================================================
    // Inner class definitions
    //==============================================================================

}


/*
    (c) Copyright 2002, 2003, 2004, 2005, 2006, 2007, 2008, 2009 Hewlett-Packard Development Company, LP
    All rights reserved.

    Redistribution and use in source and binary forms, with or without
    modification, are permitted provided that the following conditions
    are met:

    1. Redistributions of source code must retain the above copyright
       notice, this list of conditions and the following disclaimer.

    2. Redistributions in binary form must reproduce the above copyright
       notice, this list of conditions and the following disclaimer in the
       documentation and/or other materials provided with the distribution.

    3. The name of the author may not be used to endorse or promote products
       derived from this software without specific prior written permission.

    THIS SOFTWARE IS PROVIDED BY THE AUTHOR ``AS IS'' AND ANY EXPRESS OR
    IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
    OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
    IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY DIRECT, INDIRECT,
    INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT
    NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
    DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
    THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
    (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
    THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/

