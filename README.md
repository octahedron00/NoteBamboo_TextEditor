# NoteBamboo_TextEditor

### Rich Text Editor for android, specialized to write memo, only with Java.

You can use : Bold, Italic, underline, strikethrough, and color of letter(foregroundcolor).

Code works with **[Spans](https://developer.android.com/guide/topics/text/spans) in android,**

So you can add any spans you want : background color, relative size, etc.

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

I avoided to get spans that I don't care of. I specified spans to get.

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

And I moved all spans out of the interval, by checking start and end point of each span, and comparing it with start and end point of selection.

I also checked start and end point of span instances, whether these spans are continuous in interval.

(That's why I sorted them : to check ...)

If all letters in interval have same span, 

~~~java
int s1=2100000000, e1=ss;
if(a==UNDERLINE){
	for(int i=0; i<underlineSpans.length; i++){
		int s, e;
		s = spannable.getSpanStart(underlineSpans[i]);
		e = spannable.getSpanEnd(underlineSpans[i]);
		if(s<=s1){
			s1 = s;
		}
		if(s<=e1&&e1<=e){
			e1 = e;
		}
		spannable.removeSpan(underlineSpans[i]);
		if(s<ss){
			spannable.setSpan(new UnderlineSpan(), s, ss, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
		}
		if(se<e){
			spannable.setSpan(new UnderlineSpan(), se, e, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
		}
	}
	if (s1 > ss || se > e1) {
		spannable.setSpan(new UnderlineSpan(), ss, se, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
	}
}
~~~
	



Rich Text Editor is hard to make, especially in java lang.
