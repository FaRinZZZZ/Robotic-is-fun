package jp.jaxa.iss.kibo.rpc.i3;

import jp.jaxa.iss.kibo.rpc.api.KiboRpcService;

import gov.nasa.arc.astrobee.Result;
import gov.nasa.arc.astrobee.types.Point;
import gov.nasa.arc.astrobee.types.Quaternion;

/////////////////////////////////// QR READ (ZXING)

import com.google.zxing.qrcode.QRCodeReader;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.RGBLuminanceSource;
import com.google.zxing.LuminanceSource;

import android.graphics.Bitmap;
import android.util.Log;


/////////////////////////////////// COMMON LIBER JAVA
import java.lang.Exception;


/**
 * Class meant to handle commands from the Ground Data System and execute them in Astrobee
 */

public class YourService extends KiboRpcService {
    @Override
    protected void runPlan1(){
        // astrobee is undocked and the mission starts

//        int qrscan = null;
        int p = 0;
        double x,y,z = 0;
        String pos;
        double f = 11.21;
        double b = -9.80 ;
        double c = 4.79;
        final Point point2 = new Point(11.48,-9.62,4.79); // high score is 11.48 9.55 4.79
        final Quaternion quaternion2 = new Quaternion(0, -0.4492353f, -0.6738529f, 0.5866089f);
        final Point pointmove1 = new Point(10.55,-9.8,4.40);
        final Quaternion quaternionmove1 = new Quaternion(0, 0, -0.707f, 0.707f);
        final Point pointmove2 = new Point(10.55,-8.0,4.40);
        final Quaternion quaternionmove2 = new Quaternion(0, 0, -0.707f, 0.707f);
        final Point pointmove3 = new Point(10.6,-8.0,4.5);
        final Quaternion quaternionmove3 = new Quaternion(0, 0, -0.707f, 0.707f);
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        api.startMission();

        // astrobee is undocked and the mission starts
        moveToWrapper(f, b, c, 0 , 0 ,-0.707, 0.707);
//        Log.d("TRY[move]:","gu arrive la");
//        api.flashlightControlFront(1);
//        Log.d("TRY[move]:","flash!!");
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        Bitmap bMap = api.getBitmapNavCam();

        int[] intArray = new int[bMap.getWidth()*bMap.getHeight()];
        bMap.getPixels(intArray, 0, bMap.getWidth(), 0, 0, bMap.getWidth(), bMap.getHeight());
        LuminanceSource source = new RGBLuminanceSource(bMap.getWidth(), bMap.getHeight(),intArray);
        BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
        com.google.zxing.Result qrscan = null;

//        Log.d("TRY[status]:","kom lang try");

        int state = 1;

        while(state == 1){
            try {
                qrscan = new QRCodeReader().decode(bitmap);
//                Log.d("QR[status]:", "Deteched QR found");
                state = 0;
            } catch (Exception e) {
//                Log.d("QR[status]:", "NOT Deteched QR found");
                f = f+0.01;
                final Point point = new Point(f,b,c);
                final Quaternion quaternion = new Quaternion(0, 0, 0, 0);
                api.moveTo(point,quaternion,true);
//                Log.d("QR[status]:",Double.toString(f));
                state = 1;
            }
        }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        pos = qrscan.getText();
        api.sendDiscoveredQR(qrscan.getText());
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//        Log.d("QR[status]:", "jedge");

//        pos = pos.substring(1, pos.length()-1);
//
////        Log.d("QR[substring]:", "lob {}");
//
//        String[] pos_2 = pos.split(",");
//        String[] results = new String[pos_2.length];
//        for (int i = 0; i <= pos_2.length-1; i++) {
//            String a = pos_2[i];
//            a = a.substring(4, a.length());
//            results[i] = a;
////            Log.d("result[i]:", results[i]);
//        }
//
//        p = Integer.parseInt(results[0]);
//        x = Double.parseDouble(results[1]);
//        y = Double.parseDouble(results[2]);
//        z = Double.parseDouble(results[3]);

//        Log.d("QR[p]:", Integer.toString(p));
//        Log.d("QR[x]:", Double.toString(x));
//        Log.d("QR[y]:", Double.toString(y));
//        Log.d("QR[z]:", Double.toString(z));

//        api.sendDiscoveredQR(qrcode.getText());

//        Log.d("Move[final_pos]:", "moving")
//        final Quaternion quaternion3 = new Quaternion(-0.3728217f, 0, -0.3728217f, 0.7933533f);
//        final Quaternion quaternion4 = new Quaternion(-0.4870091f, 0, -0.3652569f, 0.7933533f);
//        final Quaternion quaternion5 = new Quaternion(-0.5927744f, 0, -0.3556647f, 0.7225795f);
//        final Quaternion quaternion6 = new Quaternion(-0.6882868f, 0, -0.3441434f, 0.638613f);
//        final Quaternion quaternion7 = new Quaternion(-0.7718918f, 0, -0.3308108f, 0.5429063f);
//        final Quaternion quaternion8 = new Quaternion(-0.8421396f, 0, -0.3158023f, 0.4371153f);

//        final Quaternion quaternion3 = new Quaternion(0, 0, -0.707f, 0.707f);
//        api.moveTo(point2,quaternion3,true);
//        api.moveTo(point2,quaternion4,true);
//        api.moveTo(point2,quaternion5,true);
//        api.moveTo(point2,quaternion6,true);
//        api.moveTo(point2,quaternion7,true);
//        api.moveTo(point2,quaternion8,true);
//        Log.d("Move[final_pos]:", "move1");
//        api.moveTo(point2,quaternion3,true);
//        Log.d("Move[final_pos]:", "move2");
//        moveToWrapper(x, y+0.17, z+0.242, 0, 0, -0.707, 0.707);
//        Log.d("Move[final_pos]:", "STOP");
        api.moveTo(point2,quaternion2,true);

        api.laserControl(true);

        api.takeSnapshot();

        api.laserControl(false);

        api.moveTo(pointmove1,quaternionmove1,true);

        api.moveTo(pointmove2,quaternionmove2,true);

        api.moveTo(pointmove3,quaternionmove3,true);

        api.reportMissionCompletion();
    }

    @Override
    protected void runPlan2(){
        // write here your plan 2
    }

    @Override
    protected void runPlan3(){
        // write here your plan 3
    }

    // You can add your method
    private void moveToWrapper(double pos_x, double pos_y, double pos_z,
                               double qua_x, double qua_y, double qua_z,
                               double qua_w){

        final int LOOP_MAX = 3;
        final Point point = new Point(pos_x, pos_y, pos_z);
        final Quaternion quaternion = new Quaternion((float)qua_x, (float)qua_y,
                (float)qua_z, (float)qua_w);

        Result result = api.moveTo(point, quaternion, true);

        int loopCounter = 0;
        while(!result.hasSucceeded() || loopCounter < LOOP_MAX){
            result = api.moveTo(point, quaternion, true);
            ++loopCounter;
        }
    }

}

