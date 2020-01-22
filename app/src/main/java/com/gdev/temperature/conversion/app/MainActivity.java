package com.gdev.temperature.conversion.app;
import android.annotation.SuppressLint;
import android.content.*;
import android.os.*;
import android.support.v7.app.*;
import android.support.v7.widget.*;
import android.view.*;
import android.view.animation.*;
import android.widget.*;
import com.gdev.temperature.conversion.*;

import android.support.v7.widget.Toolbar;

import java.util.Objects;

/* Temperatures.java (Konversi Suhu)
* 29 / 11 / 2017 created by Hendriyawan
* github : @github.com/Hendriyawan
* facebook : www.fb.com/hendri.glanex
* email : hendrijs44@gmail.com
*
*
*/


public class MainActivity extends AppCompatActivity
{

    private AppCompatEditText edit_temp1;
	private AppCompatEditText edit_temp2;
    private Temperatures temperature;
	private LinearLayout layout_formula;
	private TextView text_formula;
	private Animation rotate_zoom_out;
	private String[] temperatures = new String[] {
		"\u00B0C",
		"\u00B0R",
		"\u00B0F",
		"K"
	};
	
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        
        //temperature
        temperature = new Temperatures(this);
        
        layout_formula = findViewById(R.id.layout_formula);
        text_formula = findViewById(R.id.text_formula);
        //animation
        rotate_zoom_out = AnimationUtils.loadAnimation(MainActivity.this, R.anim.rotate_zoom_out);
        
        //toolbar
        setupToolbar();
         //edit text temp to conversion 1
        edit_temp1 = findViewById(R.id.edit_1_temperature_to_conversion);
        edit_temp1.setHint(SharedPrefsTemp.getTempSymbol1(MainActivity.this));
        
         //edit text temp to conversion 2
        edit_temp2 = findViewById(R.id.edit_2_temperature_to_conversion);
        edit_temp2.setHint(SharedPrefsTemp.getTempSymbol2(MainActivity.this));
        edit_temp2.setKeyListener(null);
        edit_temp2.setClickable(false);
        
        //Spinner & Adapter 1
        ArrayAdapter<String> adapter1 = new ArrayAdapter(MainActivity.this, android.R.layout.simple_spinner_item, temperatures);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        AppCompatSpinner sp_temp1 = findViewById(R.id.spinner_1_temperature_to_conversion);
        sp_temp1.setAdapter(adapter1);
        
        //set selection
        sp_temp1.setSelection(SharedPrefsTemp.getTempIndex1(MainActivity.this));
        sp_temp1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
        	@Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
            	String temp_symbol1 = temperatures[position];
                SharedPrefsTemp.setTemperature1(MainActivity.this, temp_symbol1, position);
                edit_temp1.setHint(temperatures[position]);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {
            	
            }
        });
        //spinner & adapter 2
        ArrayAdapter<String> adapter2 = new ArrayAdapter(MainActivity.this, android.R.layout.simple_spinner_item, temperatures);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        AppCompatSpinner sp_temp2 = (AppCompatSpinner) findViewById(R.id.spinner_2_temperature_to_conversion);
        sp_temp2.setAdapter(adapter2);
        sp_temp2.setSelection(SharedPrefsTemp.getTempIndex2(MainActivity.this));
        sp_temp2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
        	@Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
            	String temp_symbol2 = temperatures[position];
                SharedPrefsTemp.setTemperature2(MainActivity.this, temp_symbol2, position);
                edit_temp2.setHint(temperatures[position]);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {
            	
            }
        });
        
        //Button Count
        AppCompatButton btn_count = findViewById(R.id.count);
        btn_count.setOnClickListener(new View.OnClickListener(){
        	@SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v)
            {
            	if(Objects.requireNonNull(edit_temp1.getText()).toString().isEmpty())
                {
                	showToast(getResources().getString(R.string.message_temp_1_isempty));
                }
                else {
                	layout_formula.startAnimation(rotate_zoom_out);
                	if(layout_formula.getVisibility() == View.GONE)
                    {
                    	layout_formula.setVisibility(View.VISIBLE);
                    }
                    String symbol_temp1 = SharedPrefsTemp.getTempSymbol1(MainActivity.this);
                    String symbol_temp2 = SharedPrefsTemp.getTempSymbol2(MainActivity.this);
                    double value_to_conversion = Double.parseDouble(edit_temp1.getText().toString());

                    // C to R
                    if (symbol_temp1.equals("\u00B0C") && symbol_temp2.equals("\u00B0R"))
                    {
                    	edit_temp2.setText(temperature.CelciusToReamur(value_to_conversion));
                        text_formula.setText(temperature.getFormula("\u00B0C", "\u00B0R", value_to_conversion, temperature.CelciusToReamur(value_to_conversion)));
                    }
                    // C to F
                    else if (symbol_temp1.equals("\u00B0C") && symbol_temp2.equals("\u00B0F"))
                    {
                    	edit_temp2.setText(temperature.CelciusToFahrenheit(value_to_conversion));
                        text_formula.setText(temperature.getFormula("\u00B0C", "\u00B0F", value_to_conversion, temperature.CelciusToFahrenheit(value_to_conversion)));
                    }
                    // C to K
                    else if(symbol_temp1.equals("\u00B0C") && symbol_temp2.equals("K"))
                    {
                    	edit_temp2.setText(temperature.CelciusToKelvin(value_to_conversion));
                        text_formula.setText(temperature.getFormula("\u00B0C", "K", value_to_conversion, temperature.CelciusToKelvin(value_to_conversion)));
                    }
                    /** end celcius to (R, F, K)**/
                    
                    /** start reamur to (C, F, K)**/
                    // R to C
                    else if(symbol_temp1.equals("\u00B0R") && symbol_temp2.equals("\u00B0C"))
                    {
                    	edit_temp2.setText(temperature.ReamurToCelcius(value_to_conversion));
                        text_formula.setText(temperature.getFormula("\u00B0R", "\u00B0C", value_to_conversion, temperature.ReamurToCelcius(value_to_conversion)));
                    }
                    // R to F
                    else if(symbol_temp1.equals("\u00B0R") && symbol_temp2.equals("\u00B0F"))
                    {
                    	edit_temp2.setText(temperature.ReamurToFahrenheit(value_to_conversion));
                        text_formula.setText(temperature.getFormula("\u00B0R", "\u00B0F", value_to_conversion, temperature.ReamurToFahrenheit(value_to_conversion)));
                    }
                    // R to K
                    else if(symbol_temp1.equals("\u00B0R") && symbol_temp2.equals("K"))
                    {
                    	edit_temp2.setText(temperature.ReamurToKelvin(value_to_conversion));
                        text_formula.setText(temperature.getFormula("\u00B0R", "K", value_to_conversion, temperature.ReamurToKelvin(value_to_conversion)));
                    }
                    // F to C
                    else if(symbol_temp1.equals("\u00B0F") && symbol_temp2.equals("\u00B0C"))
                    {
                    	edit_temp2.setText(temperature.FahrenheitToCelcius(value_to_conversion));
                        text_formula.setText(temperature.getFormula("\u00B0F", "\u00B0C", value_to_conversion, temperature.FahrenheitToCelcius(value_to_conversion)));
                    }
                    // F to R
                    else if(symbol_temp1.equals("\u00B0F") && symbol_temp2.equals("\u00B0R"))
                    {
                    	edit_temp2.setText(temperature.FahrenheitToReamur(value_to_conversion));
                        text_formula.setText(temperature.getFormula("\u00B0F", "\u00B0R", value_to_conversion, temperature.FahrenheitToReamur(value_to_conversion)));
                    }
                    //if C equals C
                    else if(symbol_temp1.equals("\u00B0C") && symbol_temp2.equals("\u00B0C"))
                    {
                    	edit_temp2.setText(temperature.check_after_decimal_point(value_to_conversion));
                        text_formula.setText("\u00B0C  =  "+temperature.check_after_decimal_point(value_to_conversion));
                    }
                    //if R equals R
                    else if(symbol_temp1.equals("\u00B0R") && symbol_temp2.equals("\u00B0R"))
                    {
                    	edit_temp2.setText(temperature.check_after_decimal_point(value_to_conversion));
                        text_formula.setText("\u00B0R  =  "+temperature.check_after_decimal_point(value_to_conversion));
                    }
                    //if F equals F
                    else if(symbol_temp1.equals("\u00B0F") && symbol_temp2.equals("\u00B0F"))
                    {
                    	edit_temp2.setText(temperature.check_after_decimal_point(value_to_conversion));
                        text_formula.setText("\u00B0F  =  "+temperature.check_after_decimal_point(value_to_conversion));
                    }
                    //if K equals K
                    else if(symbol_temp1.equals("K") && symbol_temp2.equals("K"))
                    {
                    	edit_temp2.setText(temperature.check_after_decimal_point(value_to_conversion));
                        text_formula.setText("K  =  "+temperature.check_after_decimal_point(value_to_conversion));
                    }
                }
            }
        });
    }
    
    /* options menu */
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
    	getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
    	switch(item.getItemId()){
    	    case R.id.about:
				final AlertDialog.Builder builder = new AlertDialog.Builder(this);
				builder.setTitle("Tentang Aplikasi");
				builder.setMessage("Konversi Suhu 29/11/2017 by Hendriyawan\nEmail : hendrijs44@gmail.com");
				builder.setNegativeButton("Kembali", new DialogInterface.OnClickListener(){
					@Override
					public void onClick(DialogInterface dialog, int id){
						dialog.cancel();
					}
				});
				builder.show();
            break;
        }
        return super.onOptionsItemSelected(item);
    }
    
    /* setup android toolbar*/
    private void setupToolbar()
    {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle(R.string.app_name);
    }
    /* show toast*/
    public void showToast(String message)
    {
    	Toast.makeText(MainActivity.this, message, Toast.LENGTH_LONG).show();
    }
    
}
