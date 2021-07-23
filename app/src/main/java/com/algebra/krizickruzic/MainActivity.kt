package com.algebra.krizickruzic

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog

class MainActivity : AppCompatActivity( ) {

    private val TAG                  = "MainActivity"
    private val b                    = arrayOfNulls< ImageView >( 9 )
    private var igra     : Igra?     = null
    private var racunalo : Racunalo? = null
    private lateinit var tvRezRac : TextView
    private lateinit var tvRezIgr : TextView

    override fun onCreate( savedInstanceState: Bundle? ) {
        super.onCreate( savedInstanceState )
        setContentView( R.layout.activity_main )

        initWidgets( )
        imeDijalog( )
        kreni( true )
    }

    private fun initWidgets( ) {
        b[0] = findViewById( R.id.imageView0 )
        b[1] = findViewById( R.id.imageView1 )
        b[2] = findViewById( R.id.imageView2 )
        b[3] = findViewById( R.id.imageView3 )
        b[4] = findViewById( R.id.imageView4 )
        b[5] = findViewById( R.id.imageView5 )
        b[6] = findViewById( R.id.imageView6 )
        b[7] = findViewById( R.id.imageView7 )
        b[8] = findViewById( R.id.imageView8 )

        tvRezRac = findViewById( R.id.rezracunalo )
        tvRezIgr = findViewById( R.id.rezigrac )
    }

    fun igraj( view: View ) {
        val n = ( view as ImageView ).tag.toString( ).toInt( )
        if( igra!!.potez( n ) ) {
            b[n]?.setImageResource( R.drawable.kruzic )
        }

    }

    override fun onCreateOptionsMenu( menu: Menu? ): Boolean {
        menuInflater.inflate( R.menu.main_menu, menu )
        return super.onCreateOptionsMenu( menu )
    }

    override fun onOptionsItemSelected( item: MenuItem ): Boolean {
        if( item.itemId == R.id.novaigra ) {
            novaIgraDijalog( )
            return true
        }
        return super.onOptionsItemSelected( item )
    }

    fun kreni( igracJePrvi : Boolean ) {
        for( iv in b ) iv?.setImageResource( 0 )
        racunalo?.cancel( true )
        igra     = Igra( igracJePrvi )
        Log.i( TAG, "Kreiram novu igru" )
        racunalo = Racunalo( igra!!, 1000, b, tvRezIgr, tvRezRac )
        racunalo!!.execute( )
        Log.i( TAG, "Nova igra pokrenuta!" )

    }

    fun imeDijalog( ) {
        val builder = AlertDialog.Builder( this )
        builder.setTitle( "Unesite ime:" )
        val input = EditText( this )
        input.inputType = InputType.TYPE_CLASS_TEXT
        builder.setView( input )

        builder.setPositiveButton( "Postavi" ) { x, y -> findViewById< TextView >( R.id.ime ).text = input.text.toString( ) }
        builder.show( )
    }

    fun novaIgraDijalog( ) {
        val builder = AlertDialog.Builder( this )
        builder.setTitle( "Nova igra!" )
        builder.setMessage( "Tko je prvi na potezu?" )
        builder.setPositiveButton( "JA" ){ x, y -> kreni( true ) }
        builder.setNegativeButton( "Računalo" ){ x, y -> kreni( false ) }
        builder.setNeutralButton( "Zanemari ovo" ) { x, y -> }
        builder.create( ).show( )
    }
}