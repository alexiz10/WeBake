package io.cayeta.webake.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Ingredient implements Parcelable {

    private double mQuantity;

    private String mMeasurement;
    private String mIngredient;

    public Ingredient(double quantity, String measurement, String ingredient) {
        mQuantity = quantity;
        mMeasurement = measurement;
        mIngredient = ingredient;
    }

    public Ingredient(Parcel parcel) {
        mQuantity = parcel.readDouble();
        mMeasurement = parcel.readString();
        mIngredient = parcel.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(mQuantity);
        dest.writeString(mMeasurement);
        dest.writeString(mIngredient);
    }

    public static Creator<Ingredient> CREATOR = new Creator<Ingredient>() {
        @Override
        public Ingredient createFromParcel(Parcel source) {
            return new Ingredient(source);
        }

        @Override
        public Ingredient[] newArray(int size) {
            return new Ingredient[size];
        }
    };

    public double getQuantity() {
        return mQuantity;
    }

    public String getMeasurement() {
        return mMeasurement;
    }

    public String getIngredient() {
        return mIngredient;
    }

}