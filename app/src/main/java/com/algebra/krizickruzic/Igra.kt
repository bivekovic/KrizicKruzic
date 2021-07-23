package com.algebra.krizickruzic

class Igra( val igracJePrvi : Boolean ) {
    private val igrac    = mutableSetOf< Int >( )
    private val racunalo = mutableSetOf< Int >( )

    private val pobjeda = setOf(
        setOf( 0, 1, 2 ), setOf( 3, 4, 5 ), setOf( 6, 7, 8 ),
        setOf( 0, 3, 6 ), setOf( 1, 4, 7 ), setOf( 2, 5, 8 ),
        setOf( 0, 4, 8 ), setOf( 2, 4, 6 )
    )

    fun pobjedaIgraca( )    : Boolean = pobjeda.any{ igrac.containsAll( it ) }
    fun pobjedaRacunala( )  : Boolean = pobjeda.any{ racunalo.containsAll( it ) }
    fun gotovo( )           : Boolean = pobjedaIgraca( ) || pobjedaRacunala( ) || igrac.size + racunalo.size == 9

    fun igracNaPotezu( )    : Boolean = !gotovo( ) &&
            (   igracJePrvi && igrac.size==racunalo.size
                    || !igracJePrvi && igrac.size < racunalo.size )

    fun racunaloNaPotezu( ) : Boolean = !gotovo( ) &&
            (   igracJePrvi && igrac.size > racunalo.size
                    || !igracJePrvi && igrac.size == racunalo.size )

    fun potez( x : Int, igracIgra : Boolean = true ) : Boolean {
        if( igracIgra )
            return igracNaPotezu( ) && !racunalo.contains( x ) && igrac.add( x )
        else
            return racunaloNaPotezu( ) && !igrac.contains( x ) && racunalo.add( x )
    }

    fun zauzetaPolja( ) : Set< Int > = igrac + racunalo
}