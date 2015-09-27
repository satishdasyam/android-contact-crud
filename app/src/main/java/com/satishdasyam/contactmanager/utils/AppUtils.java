package com.satishdasyam.contactmanager.utils;

import android.content.res.Resources;

import com.satishdasyam.contactmanager.R;

/**
 * Created by satish on 16/8/15.
 */
public class AppUtils {

    public static int getColor(String alphabet, Resources resources) {

        if (alphabet.equalsIgnoreCase("a"))
            return resources.getColor(R.color.a);
        else if (alphabet.equalsIgnoreCase("b"))
            return resources.getColor(R.color.b);
        else if (alphabet.equalsIgnoreCase("c"))
            return resources.getColor(R.color.c);
        else if (alphabet.equalsIgnoreCase("d"))
            return resources.getColor(R.color.d);
        else if (alphabet.equalsIgnoreCase("e"))
            return resources.getColor(R.color.e);
        else if (alphabet.equalsIgnoreCase("f"))
            return resources.getColor(R.color.f);
        else if (alphabet.equalsIgnoreCase("g"))
            return resources.getColor(R.color.g);
        else if (alphabet.equalsIgnoreCase("h"))
            return resources.getColor(R.color.h);
        else if (alphabet.equalsIgnoreCase("i"))
            return resources.getColor(R.color.i);
        else if (alphabet.equalsIgnoreCase("j"))
            return resources.getColor(R.color.j);
        else if (alphabet.equalsIgnoreCase("k"))
            return resources.getColor(R.color.k);
        else if (alphabet.equalsIgnoreCase("l"))
            return resources.getColor(R.color.l);
        else if (alphabet.equalsIgnoreCase("m"))
            return resources.getColor(R.color.m);
        else if (alphabet.equalsIgnoreCase("n"))
            return resources.getColor(R.color.n);
        else if (alphabet.equalsIgnoreCase("o"))
            return resources.getColor(R.color.o);
        else if (alphabet.equalsIgnoreCase("p"))
            return resources.getColor(R.color.p);
        else if (alphabet.equalsIgnoreCase("q"))
            return resources.getColor(R.color.q);
        else if (alphabet.equalsIgnoreCase("r"))
            return resources.getColor(R.color.r);
        else if (alphabet.equalsIgnoreCase("s"))
            return resources.getColor(R.color.s);
        else if (alphabet.equalsIgnoreCase("t"))
            return resources.getColor(R.color.t);
        else if (alphabet.equalsIgnoreCase("u"))
            return resources.getColor(R.color.u);
        else if (alphabet.equalsIgnoreCase("v"))
            return resources.getColor(R.color.v);
        else if (alphabet.equalsIgnoreCase("w"))
            return resources.getColor(R.color.w);
        else if (alphabet.equalsIgnoreCase("x"))
            return resources.getColor(R.color.x);
        else if (alphabet.equalsIgnoreCase("y"))
            return resources.getColor(R.color.y);
        else if (alphabet.equalsIgnoreCase("z"))
            return resources.getColor(R.color.z);

        else
            return resources.getColor(R.color.z);

    }
}
