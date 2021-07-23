package com.algebra.krizickruzic

import android.os.AsyncTask
import android.util.Log
import android.widget.ImageView
import android.widget.TextView

class Racunalo(
    val igra                : Igra,
    val vrijemeRazmisljanja : Long,
    val ploca               : Array< ImageView? >,
    val tvRezIgr            : TextView,
    val tvRezRac            : TextView
) : AsyncTask< Void, Int, Boolean >( ) {

    private val TAG          = "Racunalo ("+ ID.get( ) + ")"

    companion object ID {
        var i : Int = 1
        private fun get( ) : Int {
            Log.i( "ID", "Kreiram novi ID: $i" )
            return i++
        }
    }

    override fun doInBackground( vararg p0: Void? ) : Boolean {
        Log.i( TAG, "Ulazim u doInBackground..." )
        while( true ) {
            Log.i(TAG, "Razmišljam...")
            Thread.sleep( vrijemeRazmisljanja )
            if( igra.gotovo( ) ) break
            if( igra.racunaloNaPotezu( ) ) {
                val n = nadjiPotez( )
                igra.potez( n, false )
                publishProgress( n )
            }
        }
        return igra.pobjedaRacunala( )
    }

    override fun onProgressUpdate( vararg potez: Int? ) {
        val n = potez[0]
        if( n!=null ) ploca[n]?.setImageResource( R.drawable.krizic )
    }

    override fun onPostExecute( result: Boolean? ) {
        Log.i( TAG, "Gotova partija..." )
        if( igra.pobjedaIgraca( ) )
            tvRezIgr.text = ""+ ( tvRezIgr.text.toString().toInt()+1 )
        else if( igra.pobjedaRacunala( ) )
            tvRezRac.text = ""+ ( tvRezRac.text.toString().toInt()+1 )
    }

    fun nadjiPotez( ) : Int {
        val odigrano = igra!!.zauzetaPolja( )
        var x        = 0
        for( i in 0..8 )
            if( !odigrano.contains( i ) ) {
                x = i
                break
            }
        return x
    }
}