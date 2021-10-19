package com.octahedron00.notebamboo_texteditor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Spannable;
import android.text.Spanned;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.StrikethroughSpan;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import java.util.Arrays;
import java.util.Comparator;

public class NoteEditActivity extends AppCompatActivity {

	int RESET = 0, BOLD = 1, ITALIC = 2, UNDERLINE = 3, STRIKETHROUGH = 4;

	EditText titleView, textInput;
	Button buttonReset, buttonBold, buttonItalic, buttonUnderline, buttonStrikethrough, buttonColorDraw;
	LinearLayout colorSelect;
	ScrollView scrollView;
	ConstraintLayout[] colorButtons;

	int[] colors = new int[]{
			0xFF000000, 0xFFE53A40, 0xFFFC913A, 0xFFEFEC05, 0xFF75D701,
			0xFF33B9F1, 0xFF0080FF, 0xFFB980F0, 0xFFFFFFFF, 0xFFAAAAAA
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_note_edit);

		titleView = findViewById(R.id.titleView);
		textInput = findViewById(R.id.textInput);
		colorSelect = findViewById(R.id.colorSelect);
		colorButtons = new ConstraintLayout[]{
				findViewById(R.id.color0), findViewById(R.id.color1),
				findViewById(R.id.color2), findViewById(R.id.color3),
				findViewById(R.id.color4), findViewById(R.id.color5),
				findViewById(R.id.color6), findViewById(R.id.color7),
				findViewById(R.id.color8), findViewById(R.id.color9)
		};
		textInput.requestFocus();
		textInput.setSelection(textInput.getText().length());

		colorSelect.setVisibility(View.GONE);

		buttonReset = findViewById(R.id.reset);
		buttonReset.setOnClickListener(v -> edit(RESET));
		buttonBold = findViewById(R.id.bold);
		buttonBold.setOnClickListener(v -> edit(BOLD));
		buttonItalic = findViewById(R.id.italic);
		buttonItalic.setOnClickListener(v -> edit(ITALIC));
		buttonUnderline = findViewById(R.id.underl);
		buttonUnderline.setOnClickListener(v -> edit(UNDERLINE));
		buttonStrikethrough = findViewById(R.id.strike);
		buttonStrikethrough.setOnClickListener(v -> edit(STRIKETHROUGH));

		buttonColorDraw = findViewById(R.id.color);
		buttonColorDraw.setOnClickListener(new View.OnClickListener() {
			boolean isOpen = false;
			@Override
			public void onClick(View v) {
				if(isOpen){
					colorSelect.setVisibility(View.GONE);
					isOpen = false;
				}
				else{
					colorSelect.setVisibility(View.VISIBLE);
					isOpen = true;
				}
			}
		});

		scrollView = findViewById(R.id.ScrollView);

		for(int i=0; i<10; i++){
			final int color = colors[i];
			colorButtons[i].setBackgroundColor(color);
			colorButtons[i].setOnClickListener(v -> editColor(color));
		}

	}



	private void edit(int a){
		int ss = textInput.getSelectionStart();
		int se = textInput.getSelectionEnd();
		final Spannable span = textInput.getText();


//		Log.i("edit_span", "edit: "+Integer.toHexString(a));

		StyleSpan[] styleSpans = span.getSpans(ss, se, StyleSpan.class);
		Arrays.sort(styleSpans, (o1, o2) -> {
			if(span.getSpanStart(o1)<span.getSpanStart(o2)){
				return 1;
			}
			return 0;
		});

		UnderlineSpan[] underlineSpans = span.getSpans(ss, se, UnderlineSpan.class);
		Arrays.sort(underlineSpans, (o1, o2) -> {
			if(span.getSpanStart(o1)<span.getSpanStart(o2)){
				return 1;
			}
			return 0;
		});

		StrikethroughSpan[] strikethroughSpans = span.getSpans(ss, se, StrikethroughSpan.class);
		Arrays.sort(strikethroughSpans, (o1, o2) -> {
			if(span.getSpanStart(o1)<span.getSpanStart(o2)){
				return 1;
			}
			return 0;
		});

		ForegroundColorSpan[] foregroundColorSpans = span.getSpans(ss, se, ForegroundColorSpan.class);
		Arrays.sort(foregroundColorSpans, (o1, o2) -> {
			if(span.getSpanStart(o1)<span.getSpanStart(o2)){
				return 1;
			}
			return 0;
		});

		BackgroundColorSpan[] backgroundColorSpans = span.getSpans(ss, se, BackgroundColorSpan.class);
		Arrays.sort(backgroundColorSpans, (o1, o2) -> {
			if(span.getSpanStart(o1)<span.getSpanStart(o2)){
				return 1;
			}
			return 0;
		});

		for(int i=0; i<styleSpans.length; i++){
			int s, e;
			s = span.getSpanStart(styleSpans[i]);
			e = span.getSpanEnd(styleSpans[i]);
//			Log.i("edit_span style", "edit: "+i+" / "+styleSpans[i].toString()+"/"+styleSpans[i].getStyle()+"/ "+s+"~"+e);
		}
		for(int i=0; i<underlineSpans.length; i++){
			int s, e;
			s = span.getSpanStart(underlineSpans[i]);
			e = span.getSpanEnd(underlineSpans[i]);
//			Log.i("edit_span under", "edit: "+i+" / "+underlineSpans[i].toString()+"/"+"/ "+s+"~"+e);
		}
		for(int i=0; i<strikethroughSpans.length; i++){
			int s, e;
			s = span.getSpanStart(strikethroughSpans[i]);
			e = span.getSpanEnd(strikethroughSpans[i]);
//			Log.i("edit_span strTh", "edit: "+i+" / "+strikethroughSpans[i].toString()+"// "+s+"~"+e);
		}
		for(int i=0; i<foregroundColorSpans.length; i++){
			int s, e;
			s = span.getSpanStart(foregroundColorSpans[i]);
			e = span.getSpanEnd(foregroundColorSpans[i]);
//			Log.i("edit_span foreG", "edit: "+i+" / "+foregroundColorSpans[i].toString()+"/"+foregroundColorSpans[i].getForegroundColor()+"/ "+s+"~"+e);
		}
		for(int i=0; i<backgroundColorSpans.length; i++) {
			int s, e;
			s = span.getSpanStart(backgroundColorSpans[i]);
			e = span.getSpanEnd(backgroundColorSpans[i]);
//			Log.i("edit_span backG", "edit: " + i + " / " + backgroundColorSpans[i].toString() + "/" + backgroundColorSpans[i].getBackgroundColor() + "/ " + s + "~" + e);
		}

		if(a==RESET){
			for(int i=0; i<styleSpans.length; i++){
				int s, e;
				s = span.getSpanStart(styleSpans[i]);
				e = span.getSpanEnd(styleSpans[i]);
				span.removeSpan(styleSpans[i]);
				if(s<ss){
					span.setSpan(new StyleSpan(styleSpans[i].getStyle()), s, ss, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
				}
				if(se<e){
					span.setSpan(new StyleSpan(styleSpans[i].getStyle()), se, e, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
				}
			}
			for(int i=0; i<underlineSpans.length; i++){
				int s, e;
				s = span.getSpanStart(underlineSpans[i]);
				e = span.getSpanEnd(underlineSpans[i]);
				span.removeSpan(underlineSpans[i]);
				if(s<ss){
					span.setSpan(new UnderlineSpan(), s, ss, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
				}
				if(se<e){
					span.setSpan(new UnderlineSpan(), se, e, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
				}
			}
			for(int i=0; i<strikethroughSpans.length; i++){
				int s, e;
				s = span.getSpanStart(strikethroughSpans[i]);
				e = span.getSpanEnd(strikethroughSpans[i]);
				span.removeSpan(strikethroughSpans[i]);
				if(s<ss){
					span.setSpan(new StrikethroughSpan(), s, ss, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
				}
				if(se<e){
					span.setSpan(new StrikethroughSpan(), se, e, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
				}
			}
			for(int i=0; i<foregroundColorSpans.length; i++){
				int s, e;
				s = span.getSpanStart(foregroundColorSpans[i]);
				e = span.getSpanEnd(foregroundColorSpans[i]);
				span.removeSpan(foregroundColorSpans[i]);
				if(s<ss){
					span.setSpan(new ForegroundColorSpan(foregroundColorSpans[i].getForegroundColor()), s, ss, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
				}
				if(se<e){
					span.setSpan(new ForegroundColorSpan(foregroundColorSpans[i].getForegroundColor()), se, e, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
				}
			}
			for(int i=0; i<backgroundColorSpans.length; i++){
				int s, e;
				s = span.getSpanStart(backgroundColorSpans[i]);
				e = span.getSpanEnd(backgroundColorSpans[i]);
				span.removeSpan(backgroundColorSpans[i]);
				if(s<ss){
					span.setSpan(new BackgroundColorSpan(backgroundColorSpans[i].getBackgroundColor()), s, ss, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
				}
				if(se<e){
					span.setSpan(new BackgroundColorSpan(backgroundColorSpans[i].getBackgroundColor()), se, e, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
				}
			}
		}

		int s1=2100000000, e1=ss;
		if(a==BOLD){
			if(styleSpans.length==0){
				span.setSpan(new StyleSpan(Typeface.BOLD), ss, se, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
				return;
			}
			for(int i=0; i<styleSpans.length; i++){
				if(styleSpans[i].getStyle()!=Typeface.BOLD){
					continue;
				}
				int s, e;
				s = span.getSpanStart(styleSpans[i]);
				e = span.getSpanEnd(styleSpans[i]);
				if(s<=s1){
					s1 = s;
				}
				if(s<=e1&&e1<=e){
					e1 = e;
				}
				span.removeSpan(styleSpans[i]);
				if(s<ss){
					span.setSpan(new StyleSpan(styleSpans[i].getStyle()), s, ss, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
				}
				if(se<e){
					span.setSpan(new StyleSpan(styleSpans[i].getStyle()), se, e, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
				}
			}
			if (s1 > ss || se > e1) {
				span.setSpan(new StyleSpan(Typeface.BOLD), ss, se, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
			}
		}

		if(a==ITALIC){
			if(styleSpans.length==0){
				span.setSpan(new StyleSpan(Typeface.ITALIC), ss, se, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
				return;
			}
			for(int i=0; i<styleSpans.length; i++){
				if(styleSpans[i].getStyle()!=Typeface.ITALIC){
					continue;
				}
				int s, e;
				s = span.getSpanStart(styleSpans[i]);
				e = span.getSpanEnd(styleSpans[i]);
				if(s<=s1){
					s1 = s;
				}
				if(s<=e1&&e1<=e){
					e1 = e;
				}
				span.removeSpan(styleSpans[i]);
				if(s<ss){
					span.setSpan(new StyleSpan(styleSpans[i].getStyle()), s, ss, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
				}
				if(se<e){
					span.setSpan(new StyleSpan(styleSpans[i].getStyle()), se, e, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
				}
			}
			if (s1 > ss || se > e1) {
				span.setSpan(new StyleSpan(Typeface.ITALIC), ss, se, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
			}
		}

		if(a==UNDERLINE){
			for(int i=0; i<underlineSpans.length; i++){
				int s, e;
				s = span.getSpanStart(underlineSpans[i]);
				e = span.getSpanEnd(underlineSpans[i]);


				if(s<=s1){
					s1 = s;
				}
				if(s<=e1&&e1<=e){
					e1 = e;
				}
				span.removeSpan(underlineSpans[i]);
				if(s<ss){
					span.setSpan(new UnderlineSpan(), s, ss, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
				}
				if(se<e){
					span.setSpan(new UnderlineSpan(), se, e, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
				}
			}
			if (s1 > ss || se > e1) {
				span.setSpan(new UnderlineSpan(), ss, se, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
			}
		}

		if(a==STRIKETHROUGH){
			for(int i=0; i<strikethroughSpans.length; i++){
				int s, e;
				s = span.getSpanStart(strikethroughSpans[i]);
				e = span.getSpanEnd(strikethroughSpans[i]);
				if(s<=s1){
					s1 = s;
				}
				if(s<=e1&&e1<=e){
					e1 = e;
				}
				span.removeSpan(strikethroughSpans[i]);
				if(s<ss){
					span.setSpan(new StrikethroughSpan(), s, ss, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
				}
				if(se<e){
					span.setSpan(new StrikethroughSpan(), se, e, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
				}
			}
			if (s1 > ss || se > e1) {
				span.setSpan(new StrikethroughSpan(), ss, se, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
			}
		}
	}

	private void editColor(int a){
		int ss = textInput.getSelectionStart();
		int se = textInput.getSelectionEnd();
		final Spannable span = textInput.getText();

		ForegroundColorSpan[] foregroundColorSpans = span.getSpans(ss, se, ForegroundColorSpan.class);
		Arrays.sort(foregroundColorSpans, new Comparator<ForegroundColorSpan>() {
			@Override
			public int compare(ForegroundColorSpan o1, ForegroundColorSpan o2) {
				if(span.getSpanStart(o1)<span.getSpanStart(o2)){
					return 1;
				}
				return 0;
			}
		});


		for(int i=0; i<foregroundColorSpans.length; i++){
			int s, e;
			s = span.getSpanStart(foregroundColorSpans[i]);
			e = span.getSpanEnd(foregroundColorSpans[i]);
			span.removeSpan(foregroundColorSpans[i]);
			if(s<ss){
				span.setSpan(new ForegroundColorSpan(foregroundColorSpans[i].getForegroundColor()), s, ss, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
			}
			if(se<e){
				span.setSpan(new ForegroundColorSpan(foregroundColorSpans[i].getForegroundColor()), se, e, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
			}
		}
		span.setSpan(new ForegroundColorSpan(a), ss, se, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);

	}


	public void clickOnBack(View v){
		textInput.requestFocus();
		textInput.setSelection(textInput.getText().length());
		Log.d("TAG", "clickOnBack: "+v.toString());
	}
}
