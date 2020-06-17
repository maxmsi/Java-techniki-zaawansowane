#include "statistics_Chi2.h"
#include <vector>
#include <algorithm>
#include <fstream>
#include <ostream>
#include <iostream>
#include <iterator>
using namespace std;

JNIEXPORT void JNICALL Java_statistics_Chi2_sayHello
  (JNIEnv * env, jobject thisObject){
        cout << "Hello from Cdsd++ !!" << endl;
  }

  JNIEXPORT jobjectArray JNICALL Java_statistics_Chi2_calculate__
    (JNIEnv * env, jobject thisObject){

   /*   df=k-1 <>  df= 4-1
   *    alfa = 0.05
   */
    double alfa = 0.05;
    double df = 3.0;
    double critical_value=7.815;
    vector <double> calculated;
    vector<double> observed;
    vector<double> excpected;


    ifstream ifile2("observed.txt", ios::in);
    //check to see that the file was opened correctly:
    if (!ifile2.is_open()) {
        cerr << "There was a problem opening the input file!\n";
        exit(1);//exit or do additional error checking
    }
    double num = 0.0;
    //keep storing values from the text file so long as data exists:
    while (ifile2 >> num) {
        observed.push_back(num);
    }

    ifstream ifile("expected.txt", ios::in);
    if (!ifile.is_open()) {
            cerr << "There was a problem opening the input file!\n";
            exit(1);//exit or do additional error checking
        }

        num = 0.0;
        //keep storing values from the text file so long as data exists:
        while (ifile >> num) {
            excpected.push_back(num);
        }

        double chi2;

        for(int i=0;i<observed.size();i++){
               //push particural results
               calculated.push_back((observed[i]-excpected[i])*(observed[i]-excpected[i])/excpected[i]);
               // count main result
               chi2 += (observed[i]-excpected[i])*(observed[i]-excpected[i])/excpected[i];
        }
        //push main result
        calculated.push_back(chi2);
        // JNI actions
        jclass doubleClass = env->FindClass("java/lang/Double");
        jobjectArray ret = (jobjectArray)env->NewObjectArray(5, doubleClass, nullptr);
        jmethodID init = env->GetMethodID(doubleClass, "<init>", "(D)V");

        for (int i = 0; i < calculated.size(); ++i)
         	{
         		env->SetObjectArrayElement(ret, i, env->NewObject(doubleClass, init, calculated[i]));
         	}

        return ret;


    }

  JNIEXPORT void JNICALL Java_statistics_Chi2_calculate___3Ljava_lang_Double_2
  (JNIEnv * env, jobject thisObj, jobjectArray obserArr){
    // Create the object of the class Chi2 class
    jsize len = env->GetArrayLength(obserArr);
    jclass doubleClass = env->FindClass("java/lang/Double");
    jmethodID valueDoubleMethodID = env->GetMethodID(doubleClass, "doubleValue", "()D");

    std::vector<double> obserVec;
    std::vector<double> expectVec;
    std:: vector<double> calculated;
    obserVec.reserve(len);
    //Get array from argument.
    for (int i = 0; i < len; i++)
        		jobject obj = env->GetObjectArrayElement(obserArr, i);
        		jdouble val = env->CallDoubleMethod(obj, valueDoubleMethodID);
        		obserVec.push_back(val);
                env->DeleteLocalRef(obj);
     }
     //Get array from instance.
    jclass cls = env->GetObjectClass(thisObj);
    jfieldID fieldArrayA = env->GetFieldID(cls, "expected", "[Ljava/lang/Double;");


  }

  JNIEXPORT jobjectArray JNICALL Java_statistics_Chi2_calculate0
    (JNIEnv * env, jobject, jobjectArray obserArr, jobjectArray expectArr){
    //Get the observed array values.
    jsize len = env->GetArrayLength(obserArr);
    jsize len2 = env->GetArrayLength(expectArr);

    jclass doubleClass = env->FindClass("java/lang/Double");
    jmethodID valueDoubleMethodID = env->GetMethodID(doubleClass, "doubleValue", "()D");

    std::vector<double> obserVec;
    std::vector<double> expectVec;
    std:: vector<double> calculated;
    obserVec.reserve(len);
    expectVec.reserve(len2);


    	for (int i = 0; i < len; i++) {
    		jobject obj = env->GetObjectArrayElement(obserArr, i);
    		jdouble val = env->CallDoubleMethod(obj, valueDoubleMethodID);
    		obserVec.push_back(val);
            env->DeleteLocalRef(obj);
            }
 	for (int i = 0; i < len2; i++) {
    		jobject obj2 = env->GetObjectArrayElement(expectArr, i);
            jdouble val2 = env->CallDoubleMethod(obj2, valueDoubleMethodID);
            expectVec.push_back(val2);
            env->DeleteLocalRef(obj2);
    	}

    double chi2;
    for(int i=0;i<obserVec.size();i++){
         //push particural results
          calculated.push_back((obserVec[i]-expectVec[i])*(obserVec[i]-expectVec[i])/expectVec[i]);
           // count main result
           chi2 += (obserVec[i]-expectVec[i])*(obserVec[i]-expectVec[i])/expectVec[i];
     }
     calculated.push_back(chi2);

     jobjectArray ret = (jobjectArray)env->NewObjectArray(len+1, doubleClass, nullptr);
     jmethodID init = env->GetMethodID(doubleClass, "<init>", "(D)V");

     for (int i = 0; i < calculated.size(); i++)
        {
         env->SetObjectArrayElement(ret, i, env->NewObject(doubleClass, init, calculated[i]));
        }

     return ret;

    }
