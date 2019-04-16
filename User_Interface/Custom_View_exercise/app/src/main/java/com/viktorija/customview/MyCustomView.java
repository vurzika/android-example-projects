package com.viktorija.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

// Create a new class, name it “MyCustomView”, and extend in by View class
public class MyCustomView extends View {

    // Make mRect and mPaint objects of Rect and Paint class respectively as global to the class
    Rect mRect;
    Paint mPaint;
    // declaring an int variable mSquareColor
    int mSquareColor;

    // Making a global variable mPadding that adds/subtracts to mRect dimensions accordingly
    int mPadding = 0;

    // On clicking the prompt, you should select all the options for the constructor
    public MyCustomView(Context context) {
        super(context);
        init(null);
    }

    // provide a constructor that takes a Context and an AttributeSet object as parameters
    // This constructor allows the layout editor to create and edit an instance of your view.
    public MyCustomView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public MyCustomView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    public MyCustomView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs);
    }

    // create a new function void init(@Nullable AttributeSet set) with blank body and make all the
    // constructors access this function by calling init(attrs) on all constructors
    // except you have to pass null in the first constructor) ( to be used later).
    private void init(@Nullable AttributeSet set){
        // making instances of mPaint and mRect;
        mPaint = new Paint (Paint.ANTI_ALIAS_FLAG);
        mRect = new Rect();

        // checking in our init() method whether the AttributeSet set being passed as a parameter is null or not.
        if (set == null) {
            return;
        }

        // If set parameter is not null, then we obtain a TypedArray typedArray (say) by calling
        // obtainStyledAttributes(set, R.styleable.MyCustomView) using getContext();
        TypedArray ta = getContext().obtainStyledAttributes(set, R.styleable.MyCustomView);
        // Initializing mSquareColor with the values input through the TypedArray ta
        // providing the default colour in case no value for that attribute was input by the user
        mSquareColor = ta.getColor(R.styleable.MyCustomView_square_color, Color.GREEN);
        mPaint.setColor(mSquareColor);
        // call ta.recycle() once you are done accessing it
        ta.recycle();
    }

    // Create empty functions in MyCustomView.class and call them on respective onClicks of buttons in activity_main.xml.
    // Provide implementations for functions to make modifications, and then refresh the custom view by calling postInvalidate();
    public void swapColor() {
        mPaint.setColor(mPaint.getColor() == mSquareColor ?
                Color.RED:
                mSquareColor);
        postInvalidate();
    }

    public void customPaddingUp (int padding) {
        mPadding = mPadding + padding;
        postInvalidate();
    }

    public void customPaddingDown (int padding) {
        mPadding = mPadding - padding;
        postInvalidate();
    }


    // Override the onDraw(Canvas canvas) in this class
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // Assign left, right, top, bottom coordinates to React object
        mRect.left = 0 + mPadding;
        mRect.right = getWidth() - mPadding;
        mRect.top = 0 + mPadding;
        mRect.bottom = getHeight() - mPadding;

        canvas.drawRect(mRect, mPaint);
    }
}
