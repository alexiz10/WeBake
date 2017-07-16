package io.cayeta.webake.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Recipe implements Parcelable {

    private int mId;
    private int mServings;

    private String mName;
    private String mImage;

    private ArrayList<Ingredient> mIngredients;
    private ArrayList<Instruction> mInstructions;

    public Recipe(int id, int servings, String name, String image, ArrayList<Ingredient> ingredients, ArrayList<Instruction> instructions) {
        mId = id;
        mServings = servings;
        mName = name;
        mImage = image;
        mIngredients = ingredients;
        mInstructions = instructions;
    }

    public Recipe(Parcel parcel) {
        mId = parcel.readInt();
        mServings = parcel.readInt();
        mName = parcel.readString();
        mImage = parcel.readString();
        mIngredients = parcel.readArrayList(Ingredient.class.getClassLoader());
        mInstructions = parcel.readArrayList(Instruction.class.getClassLoader());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mId);
        dest.writeInt(mServings);
        dest.writeString(mName);
        dest.writeString(mImage);
        dest.writeList(mIngredients);
        dest.writeList(mInstructions);
    }

    public static Creator<Recipe> CREATOR = new Creator<Recipe>() {
        @Override
        public Recipe createFromParcel(Parcel source) {
            return new Recipe(source);
        }

        @Override
        public Recipe[] newArray(int size) {
            return new Recipe[size];
        }
    };

    public int getId() {
        return mId;
    }

    public int getServings() {
        return mServings;
    }

    public String getName() {
        return mName;
    }

    public String getImage() {
        return mImage;
    }

    public ArrayList<Ingredient> getIngredients() {
        return mIngredients;
    }

    public ArrayList<Instruction> getInstructions() {
        return mInstructions;
    }

}