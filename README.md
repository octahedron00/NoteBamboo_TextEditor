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
//	spannable = text with spans
~~~

this function can get any spans, but there are some spans that we need to use.

I avoided to get spans that I don't care of. I specified spans to get.

For example, to get UnderlineSpan, I used following code.

~~~java
	UnderlineSpan[] underlineSpans = span.getSpans(ss, se, UnderlineSpan.class);
~~~

By using each span instances, you can get its starting and ending point in spannable.

After getting spans, I **sorted them by starting point.**

~~~java
	Arrays.sort(underlineSpans, new Comparator<UnderlineSpan>() {
		@Override
		public int compare(UnderlineSpan o1, UnderlineSpan o2) {
			if(span.getSpanStart(o1)<span.getSpanStart(o2)){
				return 1;
			}
			return 0;
		}
	});
~~~


	



Rich Text Editor is hard to make, especially in java lang.
