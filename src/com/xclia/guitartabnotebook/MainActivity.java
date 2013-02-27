package com.xclia.guitartabnotebook;

import java.util.HashMap;
import java.util.Map;

import android.os.Bundle;
import android.app.Activity;
import android.util.SparseIntArray;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	private String[] keys = { "<", "_", "0", "1", "2", "3", "4", "5", "6", "7",
			"8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18",
			"19", "20", "21", "b", "h", "p", "/" };
	private int largestLength = 0;
	private ListView lvNotesInput;
	private RadioGroup rgStrings;
	private TextView[] t = new TextView[6];// E,tA,tD,tG,tB,tEe;
	private StringBuilder[] s= new StringBuilder[6];
	private int[] sLast = new int[6];
	private SparseIntArray tuning = new SparseIntArray();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		for (int i = 0; i < 6; i++){
			s[i] = new StringBuilder();
			sLast[i]=-1;
		}

		tuning.put(R.id.r0, 0);
		tuning.put(R.id.r1, 1);
		tuning.put(R.id.r2, 2);
		tuning.put(R.id.r3, 3);
		tuning.put(R.id.r4, 4);
		tuning.put(R.id.r5, 5);

		rgStrings = (RadioGroup) findViewById(R.id.rgStringSelect);

		t[0] = (TextView) findViewById(R.id.t0);
		t[1] = (TextView) findViewById(R.id.t1);
		t[2] = (TextView) findViewById(R.id.t2);
		t[3] = (TextView) findViewById(R.id.t3);
		t[4] = (TextView) findViewById(R.id.t4);
		t[5] = (TextView) findViewById(R.id.t5);

		lvNotesInput = (ListView) findViewById(R.id.lvNotesInput);
		lvNotesInput.setAdapter(new ArrayAdapter<String>(MainActivity.this,
				android.R.layout.simple_list_item_1, keys));
		lvNotesInput.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adp, View view,
					int position, long id) {
				int rId = rgStrings.getCheckedRadioButtonId();
				if (rId != -1)
					executeCmd(tuning.get(rId), position);
				else
					Toast.makeText(MainActivity.this, "Choose a string",
							Toast.LENGTH_SHORT).show();
			}
		});

	}

	private void executeCmd(int sPos, int pos) {

		if (pos > 0)
			insert(sPos, pos);
	}

	private void toastInvalid() {
		Toast.makeText(MainActivity.this, "Invalid", Toast.LENGTH_SHORT).show();
	}

	private boolean isInputValid(int sPos, int pos) {
		boolean ret = (pos > 23 && (sLast[sPos] > 23 || sLast[sPos]==1 || sLast[sPos]==-1)) ? false : true;
		if (ret == false)
			toastInvalid();
		return ret;
	}

	private void insert(int sPos, int pos) {
		if (isInputValid(sPos, pos)) {
			int sLen = s[sPos].length();
			if (sLen < largestLength)
				for (int i = 0; i < largestLength - sLen+1; i++)
					s[sPos].append("_");

			//s[sPos].append("-");
			s[sPos].append(keys[pos]);
			//if(pos>11 && pos<=23)
				s[sPos].append("_");
			sLast[sPos] = pos;
			sLen = s[sPos].length();
			if (sLen > largestLength)
				largestLength = sLen;
			t[sPos].setText(s[sPos].toString());
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
