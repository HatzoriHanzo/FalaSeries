package com.example.falaserie.activities.Util;

import android.Manifest;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.os.BatteryManager;
import android.os.Build;
import android.os.Environment;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

//import com.google.zxing.integration.android.IntentIntegrator;
//
//import org.apache.commons.lang3.StringUtils;
//import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import com.example.falaserie.R;
//import mobi.audax.nutricao.bo.AgendaBo;
//import mobi.audax.nutricao.bo.AutorizacaoBo;
//import mobi.audax.nutricao.bo.AvaliacaoBo;
//import mobi.audax.nutricao.callback.DialogSimpleCallback;
//import mobi.audax.nutricao.callback.ProgressCallback;
import mobi.stos.httplib.HttpAsync;
import mobi.stos.httplib.inter.FutureCallback;

import static android.content.Context.MODE_PRIVATE;

public class Util {

    private static boolean dirChecker(String name) {
        return new File(name).mkdir();
    }

    @NonNull
    public static String jsonToString(String path) {
        StringBuilder builder = new StringBuilder();
        BufferedReader br = null;
        FileReader fr = null;
        try {
            fr = new FileReader(path);
            br = new BufferedReader(fr);

            String sCurrentLine;
            while ((sCurrentLine = br.readLine()) != null) {
                builder.append(sCurrentLine);
            }

        } catch (IOException e) {

            e.printStackTrace();

        } finally {

            try {

                if (br != null)
                    br.close();

                if (fr != null)
                    fr.close();

            } catch (IOException ex) {

                ex.printStackTrace();

            }

        }

        return builder.toString();
    }

//    public static boolean temEnvioPendente(Context context) {
//
////        final AutorizacaoBo autorizacaoBo = new AutorizacaoBo(context);
////        final AvaliacaoBo avaliacaoBo = new AvaliacaoBo(context);
////        final AgendaBo agendaBo = new AgendaBo(context);
////
////        int autorizacaoCount = autorizacaoBo.count(null, null);
////        int avaliacaoCount = avaliacaoBo.count(null, null);
////        int agendaCount = agendaBo.count("sincronizado = ?", new String[]{"0"});
//
//        int totalChecklist = autorizacaoCount +
//                avaliacaoCount +
//                agendaCount;
//
//        boolean temPendente = false;
//
//        if (totalChecklist > 0) {
//            temPendente = true;
//        }
//        return temPendente;
//    }

//    public static String getUniqueIMEIId(Context context) {
//        try {
//            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
//            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
//                return "";
//            }
//            String imei = telephonyManager.getDeviceId();
//            Log.e("imei", "=" + imei);
//            if (imei != null && !imei.isEmpty()) {
//                return imei;
//            } else {
//                return android.os.Build.SERIAL;
//            }
//        } catch (Exception e) {
//            return Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
//        }
//    }

//    public static void startQrCodeScan(Activity activity) {
//        IntentIntegrator integrator = new IntentIntegrator(activity);
//        integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
//        integrator.setPrompt(activity.getString(R.string.mensagem_ler_qrcode));
//        integrator.setCameraId(0);
//        integrator.setBeepEnabled(true);
//        integrator.setBarcodeImageEnabled(true);
//        integrator.initiateScan();
//    }

//    public static void unzip(String _zipFile, String _targetLocation, ProgressCallback progressCallback) {
//        dirChecker(_targetLocation);
//
//        try {
//            // region get all complete file sizes
//            List<ZipEntry> entries = new ArrayList<>();
//            ZipFile zf = new ZipFile(_zipFile);
//            Enumeration e = zf.entries();
//            while (e.hasMoreElements()) {
//                ZipEntry ze = (ZipEntry) e.nextElement();
//                if (ze.isDirectory()) {
//                    dirChecker(ze.getName());
//                } else {
//                    entries.add(ze);
//                }
//            }
//            // endregion
//
//            FileInputStream fin = new FileInputStream(_zipFile);
//            ZipInputStream zin = new ZipInputStream(fin);
//            ZipEntry ze = null;
//
//            int i = 0;
//            while ((ze = zin.getNextEntry()) != null) {
//                if (ze.isDirectory()) {
//                    dirChecker(ze.getName());
//                } else {
//                    ZipEntry entry = entries.get(i);
//
//                    FileOutputStream fos = new FileOutputStream(_targetLocation + File.separator + ze.getName());
//                    final byte[] buf = new byte[1024];
//                    int bytesRead;
//                    long nread = 0L;
//                    long length = entry.getSize();
//
//                    while (-1 != (bytesRead = zin.read(buf))) {
//                        fos.write(buf, 0, bytesRead);
//                        nread += bytesRead;
//                        int currentProgress = (int) (nread * 100 / length);
//                        if (progressCallback != null) {
//                            progressCallback.onProgress(currentProgress, R.string.extraindo_arquivos);
//                        }
//                    }
//
//                    i++;
//                }
//            }
//            zin.close();
//
//            if (progressCallback != null) {
//                progressCallback.onProgress(100, R.string.extracao_concluida);
//            }
//
//        } catch (Exception e) {
//            System.out.println(e);
//        }
//    }

//    public static String sha1(String password) throws NoSuchAlgorithmException, UnsupportedEncodingException {
//
//        MessageDigest crypt = MessageDigest.getInstance("SHA-1");
//        crypt.reset();
//        crypt.update(password.getBytes("UTF-8"));
//
//        return new BigInteger(1, crypt.digest()).toString(16);
//    }
//
//    public static void alert(Context context, int title, int message) {
//        alert(context, context.getString(title), context.getString(message), null, null);
//    }
//
//    public static void alert(Context context, int title, int message, int button) {
//        alert(context, context.getString(title), context.getString(message), context.getString(button), null);
//    }

//    public static void alert(Context context, int title, int message, DialogSimpleCallback callback) {
//        alert(context, context.getString(title), context.getString(message), null, callback);
//    }
//
//    public static void alert(Context context, int title, int message, int button, DialogSimpleCallback callback) {
//        alert(context, context.getString(title), context.getString(message), context.getString(button), callback);
//    }
//
//    public static void alert(Context context, int title, int message, int button, DialogSimpleCallback callback, int button2, DialogSimpleCallback callback2) {
//        alert(context, context.getString(title), context.getString(message), context.getString(button), callback, context.getString(button2), callback2);
//    }
//
//    public static void alert(Context context, int title, int message, int button, DialogSimpleCallback callback, int button2) {
//        alert(context, context.getString(title), context.getString(message), context.getString(button), callback, context.getString(button2), null);
//    }

//    public static void alert(Context context, String title, String message) {
//        alert(context, title, message, null, null);
//    }
//
//    public static void alert(Context context, int title, String message) {
//        alert(context, context.getString(title), message, null, null);
//    }
//
//    public static void alert(Context context, String title, String message, String button) {
//        alert(context, title, message, button, null);
//    }
//
//    public static void alert(Context context, String title, String message, String buttton, DialogSimpleCallback callback) {
//        alert(context, title, message, buttton, callback, null, null);
//    }
//
//    public static void alert(Context context, String title, String message, String buttton, DialogSimpleCallback callback, String button2, DialogSimpleCallback callback2) {
//        if (TextUtils.isEmpty(buttton)) {
//            buttton = context.getString(R.string.fechar);
//        }

//        AlertDialog dialog = new AlertDialog.Builder(context).create();
//        dialog.setTitle(title);
//        dialog.setMessage(message);
//        dialog.setButton(DialogInterface.BUTTON_POSITIVE, buttton, (dialog13, which) -> {
//            dialog13.dismiss();
//            if (callback != null) {
//                callback.onCallback();
//            }
//        });
//        if (button2 != null) {
//            dialog.setButton(DialogInterface.BUTTON_NEGATIVE, button2, (dialog1, which) -> {
//                dialog1.dismiss();
//                if (callback2 != null) {
//                    callback2.onCallback();
//                }
//            });
//            dialog.setOnShowListener(dialog1 -> {
//                dialog.getButton(DialogInterface.BUTTON_NEGATIVE).setTextColor(ContextCompat.getColor(context, android.R.color.holo_red_dark));
//            });
//        }
//        dialog.show();
//        dialog.setCancelable(false);
//    }

    /**
     * Função redimensiona a imagem em um tamanho menor.
     */
    public static Bitmap redimensionarImagem(Bitmap bitmap, int width, int height) {
        try {
            if (width == bitmap.getWidth() && height == bitmap.getHeight()) {
                return bitmap;
            }

            if (bitmap.getWidth() > bitmap.getHeight()) { // a imagem está rodada então inverter w por h
                int w = width;
                int h = height;

                height = w;
                width = h;
            }

            int imgW = bitmap.getWidth();
            int imgH = bitmap.getHeight();
            if (width > imgW && height > imgH) {
                width = imgW;
                height = imgH;
            } else {
                double scale1 = Double.parseDouble(String.valueOf(width)) / Double.parseDouble(String.valueOf(imgW));
                double scale2 = Double.parseDouble(String.valueOf(height)) / Double.parseDouble(String.valueOf(imgH));
                double scale = (scale1 > scale2) ? scale2 : scale1;

                Long w = Math.round(imgW * scale);
                Long h = Math.round(imgH * scale);

                width = w.intValue();
                height = h.intValue();
            }

            return Bitmap.createScaledBitmap(bitmap, width, height, true);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Não foi possível redimencionar o arquivo!");
            return bitmap;
        }
    }

    public static Bitmap rotateBitmap(Bitmap source, float angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
    }

    public static int getCameraPhotoOrientation(String imagePath) {
        int rotate = 0;
        try {
            File imageFile = new File(imagePath);
            ExifInterface exif = new ExifInterface(imageFile.getAbsolutePath());
            int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);

            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_270:
                    rotate = 270;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    rotate = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_90:
                    rotate = 90;
                    break;
            }

            Log.i("RotateImage", "Exif orientation: " + orientation);
            Log.i("RotateImage", "Rotate value: " + rotate);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rotate;
    }

    public static String decrypt(String textEncrypt) throws Exception {

        final String IV = "-EX8+39-cDbgh2dJ";
        final String KEY = "Q3=L!xCF^&&qwC_7$a@2!nMMd!WC^2-+";

        Cipher ciper = Cipher.getInstance("AES/CBC/PKCS5Padding");
        SecretKeySpec key = new SecretKeySpec(KEY.getBytes("UTF-8"), "AES");
        IvParameterSpec iv = new IvParameterSpec(IV.getBytes("UTF-8"), 0, ciper.getBlockSize());
        ciper.init(Cipher.DECRYPT_MODE, key, iv);
        byte[] text = Base64.decode(textEncrypt, Base64.DEFAULT);
        return new String(text, StandardCharsets.UTF_8);
    }

    public static boolean isTimeAutomatic(Context c) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            return Settings.Global.getInt(c.getContentResolver(), Settings.Global.AUTO_TIME, 0) == 1;
        } else {
            return android.provider.Settings.System.getInt(c.getContentResolver(), android.provider.Settings.System.AUTO_TIME, 0) == 1;
        }
    }

    public static double haversine(double orglat, double orglon, double destlat, double destlon) {
        orglat = orglat * Math.PI / 180;
        orglon = orglon * Math.PI / 180;
        destlat = destlat * Math.PI / 180;
        destlon = destlon * Math.PI / 180;

        double raioterra = 6378140; // METROS
        double dlat = destlat - orglat;
        double dlon = destlon - orglon;
        double a = Math.pow(Math.sin(dlat / 2), 2) + Math.cos(orglat) * Math.cos(destlat) * Math.pow(Math.sin(dlon / 2), 2);
        double distancia = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return raioterra * distancia;
    }

    @Nullable
    public static String imageToBase64(String path) {
        if (TextUtils.isEmpty(path)) {
            return null;
        } else {
            try {
                BitmapFactory.Options bmOptions = new BitmapFactory.Options();
                Bitmap bitmap = BitmapFactory.decodeFile(path, bmOptions);
                bitmap = Util.rotateBitmap(bitmap, Util.getCameraPhotoOrientation(path));
                Bitmap bitmapRedimensionada = Util.redimensionarImagem(bitmap, 1920, 1080);
                ByteArrayOutputStream byteArrayOS = new ByteArrayOutputStream();
                bitmapRedimensionada.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOS);
                return Base64.encodeToString(byteArrayOS.toByteArray(), Base64.NO_WRAP);
            } catch (Exception e) {
                return null;
            }
        }
    }

    public static byte[] readFile(File file) {
        try (RandomAccessFile f = new RandomAccessFile(file, "r")) {
            long longlength = f.length();
            int length = (int) longlength;
            if (length != longlength)
                throw new IOException("File size >= 2 GB");
            byte[] data = new byte[length];
            f.readFully(data);
            return data;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new byte[0];
    }

//    public static JSONArray pathToBase64(String paths) {
//        JSONArray jsonArray = new JSONArray();
//        if (StringUtils.isNotBlank(paths)) {
//            try {
//                paths = paths.replaceAll("\\[", "").replaceAll("]", "");
//                String[] array = paths.split(",");
//                for (String path : array) {
//                    path = path.trim();
//                    if (StringUtils.isNotBlank(path)) {
//                        if (org.apache.commons.codec.binary.Base64.isBase64(path)) {
//                            jsonArray.put(path);
//                        } else {
//                            jsonArray.put(fileToBase64(new File(path)));
//                        }
//                    }
//                }
//            } catch (Exception e) {
//                //
//                e.printStackTrace();
//            }
//        }
//        return jsonArray;
//    }

    public static String fileToBase64(File photoFile) {
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inPreferredConfig = Bitmap.Config.RGB_565;
        bmOptions.inDither = true;
        Bitmap myBitmap = BitmapFactory.decodeFile(photoFile.getAbsolutePath(), bmOptions);
        myBitmap = Util.rotateBitmap(myBitmap, Util.getCameraPhotoOrientation(photoFile.getAbsolutePath()));
        myBitmap = Util.redimensionarImagem(myBitmap, 480, 720);

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        myBitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
        return Base64.encodeToString(bos.toByteArray(), Base64.NO_WRAP);
    }

    public static String bitmapToBase64(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOS = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOS);
        return Base64.encodeToString(byteArrayOS.toByteArray(), Base64.NO_WRAP);
    }

//    public static void capturarHora(Context context) {
//        SharedPreferences.Editor editor = context.getSharedPreferences(Constants.PREFERENCES, MODE_PRIVATE).edit();
//        editor.putLong(Constants.TIME, new Date().getTime());
//        editor.apply();
//    }

    public static float batteryLevel(Context context) {
        IntentFilter ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        Intent battery = context.registerReceiver(null, ifilter);
        if (battery != null) {
            int level = battery.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
            int scale = battery.getIntExtra(BatteryManager.EXTRA_SCALE, -1);

            return (level / (float) scale) * 100;
        } else {
            return -1;
        }
    }

    public static boolean isMyServiceRunning( Context context, Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    public static void deleteFotoUltimos5Min() {

        try {
            File[] images = new File(Environment.getExternalStorageDirectory()
                    + File.separator + "DCIM/Camera").listFiles();
            File latestSavedImage = images[0];
            for (int i = 1; i < images.length; ++i) {
                if (images[i].lastModified() > (new Date().getTime()) - 300000) {
                    latestSavedImage = images[i];
                    latestSavedImage.delete();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static String getDeviceName() {
        String manufacturer = Build.MANUFACTURER;
        String model = Build.MODEL;
        if (model.startsWith(manufacturer)) {
            return capitalize(model);
        }
        return capitalize(manufacturer) + " " + model;
    }

    private static String capitalize(String str) {
        if (TextUtils.isEmpty(str)) {
            return str;
        }
        char[] arr = str.toCharArray();
        boolean capitalizeNext = true;

        StringBuilder phrase = new StringBuilder();
        for (char c : arr) {
            if (capitalizeNext && Character.isLetter(c)) {
                phrase.append(Character.toUpperCase(c));
                capitalizeNext = false;
                continue;
            } else if (Character.isWhitespace(c)) {
                capitalizeNext = true;
            }
            phrase.append(c);
        }

        return phrase.toString();
    }

    public static int calculaIdade(java.util.Date dataNasc) {

        Calendar dataNascimento = Calendar.getInstance();
        dataNascimento.setTime(dataNasc);
        Calendar hoje = Calendar.getInstance();

        int idade = hoje.get(Calendar.YEAR) - dataNascimento.get(Calendar.YEAR);

        if (hoje.get(Calendar.MONTH) < dataNascimento.get(Calendar.MONTH)) {
            idade--;
        }
        else
        {
            if (hoje.get(Calendar.MONTH) == dataNascimento.get(Calendar.MONTH) && hoje.get(Calendar.DAY_OF_MONTH) < dataNascimento.get(Calendar.DAY_OF_MONTH)) {
                idade--;
            }
        }

        return idade;
    }

//    public static void checkHash(Context context, Usuario usuario) {
//        try {
//            SharedPreferences preferences = context.getSharedPreferences(Constants.PREFERENCES, MODE_PRIVATE);
//            HttpAsync httpAsync = new HttpAsync(new URL(preferences.getString(Constants.BASE_URL, "") + "usuario/checkHash  "));
//            String basic = Base64.encodeToString(context.getString(R.string.basic_auth).getBytes(), Base64.NO_WRAP);
//            httpAsync.addParam("hash", usuario.getHash());
//            httpAsync.addParam("id", usuario.getId());
//            httpAsync.addHeader("Authorization", "Basic " + basic);
//            httpAsync.setDebug(true);
//            httpAsync.post(new FutureCallback() {
//                @Override
//                public void onBeforeExecute() {
//
//                }
//
//                @Override
//                public void onAfterExecute() {
//
//                }
//
//                @Override
//                public void onSuccess(int responseCode, Object object) {
//                    switch (responseCode) {
//                        case 412:
//                            SharedPreferences.Editor editor = context.getSharedPreferences(Constants.PREFERENCES, MODE_PRIVATE).edit();
//                            editor.putBoolean(Constants.HASH_EXPIRED, true);
//                            editor.apply();
//                            break;
//                    }
//                }
//
//                @Override
//                public void onFailure(Exception exception) {
//
//                }
//            });
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//    }
}
