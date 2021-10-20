# NoteBamboo_TextEditor

### WYSIWYG Rich Text Editor for android, specialized to write memo, only with Java.

You can use : Bold, Italic, underline, strikethrough, and color of letter(foregroundcolor).

Code works with **[Spans](https://developer.android.com/guide/topics/text/spans) in android,**

So you can add anything works with span you want : background color, relative size, etc.

[Memo here](memo.md)

---

## How to use

You know, by using Android Studio, you can add a new activity.

**This codes are about app with only one activity, the text editor.**

You can add this activity by simply copying codes, and changing them a little bit.

Also you can add anything works with span you want.

**Code here : [NoteEditActivity.java](NoteEditActivity.java), [activity_note_edit.xml](activity_note_edit.xml)**

***No copyrights here***, just please let me know you will use this code.

---

## How it works?

using **spans** and **spannable text**, you can use this function.

~~~java
Spannable spannable = editText.getText();
int ss = textInput.getSelectionStart();
int se = textInput.getSelectionEnd();
spannable.getSpans(ss, se, Span.class) : Span[]
// spannable = text with spans
~~~

this function can get any spans, but there are some spans that we need to use.

I avoided to get spans that I don't care of or that can make something wrong. I specified spans to get.

For example, to get UnderlineSpan, I used following code.

~~~java
UnderlineSpan[] underlineSpans = spannable.getSpans(ss, se, UnderlineSpan.class);
~~~

By using each span instances, you can get its starting and ending point in spannable.

After getting spans, I **sorted them by starting point.**

~~~java
Arrays.sort(underlineSpans, new Comparator<UnderlineSpan>() {
	@Override
	public int compare(UnderlineSpan o1, UnderlineSpan o2) {
		if(spannable.getSpanStart(o1)<spannable.getSpanStart(o2)){
			return 1;
		}
		return 0;
	}
});
~~~

**And I moved all spans out of the interval**,

by checking start and end point of each span, 

and comparing it with start and end point of selection.

**I also checked start and end point of span instances, whether these spans are continuous in interval or not.**

(That's why I sorted them : **[Sweep line algorithm](https://en.wikipedia.org/wiki/Sweep_line_algorithm)**)

Only if all letters in interval don't have same span, we add new span in the interval.

~~~java
int s1=2100000000, e1=ss;
if(a==UNDERLINE){
	for(int i=0; i<underlineSpans.length; i++){
		int s = spannable.getSpanStart(underlineSpans[i]);
		int e = spannable.getSpanEnd(underlineSpans[i]);
		if(s<=s1){
			s1 = s;
			// set the start point, to compare with the selection interval.
		}
		if(s<=e1&&e1<=e){
			e1 = e;
			// set the end point of continuous span, to compare with the selection interval.
		}
		spannable.removeSpan(underlineSpans[i]);
		if(s<ss){
			spannable.setSpan(new UnderlineSpan(), s, ss, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
			// add new span, with removal works like cutting the span.
			// only if the span goes under and over start point.
		}
		if(se<e){
			spannable.setSpan(new UnderlineSpan(), se, e, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
			// same with upper one : only if the span goes under and over end point.
		}
	}
	if (s1 > ss || se > e1) {
		spannable.setSpan(new UnderlineSpan(), ss, se, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
		// add span on this interval, if original spans don't make full of it.
	}
}
~~~

That's all, that's how this program works with span.
