package node8.valetuncle.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.view.inputmethod.InputConnectionWrapper;
import android.widget.EditText;

import java.util.Random;

/**
 * Created by Node8 on 5/10/15.
 */
public class ZanyEditText extends EditText {
    public boolean backButtonPressed = false;
    public Random r = new Random();
    private EditText prevEditText;
    private EditText nextEditText;
    public EditText getPrevEditText() {
        return prevEditText;
    }
    public void setPrevEditText(EditText prevEditText) {
        this.prevEditText = prevEditText;
    }
    public EditText getNextEditText() {
        return nextEditText;
    }
    public void setNextEditText(EditText nextEditText) {
        this.nextEditText = nextEditText;
    }
    public ZanyEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }
    public ZanyEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    public ZanyEditText(Context context) {
        super(context);
    }
    public void onBackButtonPressed() {
        if ((this.getText().toString().length() == 0)&&(prevEditText != null))
            prevEditText.requestFocus();
    }
    @Override
    public InputConnection onCreateInputConnection(EditorInfo outAttrs) {
        return new ZanyInputConnection(super.onCreateInputConnection(outAttrs),
                true);
    }
    private class ZanyInputConnection extends InputConnectionWrapper {
        public ZanyInputConnection(InputConnection target, boolean mutable) {
            super(target, mutable);
        }
        @Override
        public boolean sendKeyEvent(KeyEvent event) {
            if (event.getAction() == KeyEvent.ACTION_DOWN
                    && event.getKeyCode() == KeyEvent.KEYCODE_DEL) {
                ZanyEditText.this.onBackButtonPressed();
                // Un-comment if you wish to cancel the backspace:
                // return false;
            }
            return super.sendKeyEvent(event);
        }
        @Override
        public boolean deleteSurroundingText(int beforeLength, int afterLength) {
            // magic: in latest Android, deleteSurroundingText(1, 0) will be called for backspace
            if (beforeLength == 1 && afterLength == 0) {
                // backspace
                return sendKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_DEL))
                        && sendKeyEvent(new KeyEvent(KeyEvent.ACTION_UP, KeyEvent.KEYCODE_DEL));
            }
            return super.deleteSurroundingText(beforeLength, afterLength);
        }
    }
}
