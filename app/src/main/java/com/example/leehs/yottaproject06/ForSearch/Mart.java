package com.example.leehs.yottaproject06.ForSearch;

import android.location.Location;
import android.util.Log;

import com.example.leehs.yottaproject06.InfoActivity;

import java.util.ArrayList;

public class Mart{
    public static ArrayList<String> MART_NAME = null;
    public static ArrayList<Double> MART_LATITUDE = null;
    public static ArrayList<Double> MART_LONGITUDE = null;

    //////////////////////////////////EMART/////////////////////////////////
    //이마트 안양
    private static final double EMART_ANYANG_LATITUDE = 37.3969992;
    private static final double EMART_ANYANG_LONGITUDE = 126.9406083;
    //이마트 산본
    private static final double EMART_SANBON_LATITUDE = 37.361247;
    private static final double EMART_SANBON_LONGITUDE = 126.931486;
    //이마트 춘천
    private static final double EMART_CHUNCHEON_LATITUDE = 37.863669;
    private static final double EMART_CHUNCHEON_LONGITUDE = 127.71696;
    //이마트 이수
    private static final double EMART_ISU_LATITUDE = 37.484517;
    private static final double EMART_ISU_LONGITUDE = 126.980418;
    //이마트 용산
    private static final double EMART_YONGSAN_LATITUDE = 37.529497;
    private static final double EMART_YONGSAN_LONGITUDE = 126.965521;
    //이마트 여의도
    private static final double EMART_YEOUIDO_LATITUDE = 37.518601;
    private static final double EMART_YEOUIDO_LONGITUDE = 126.926500;
    //이마트 양재
    private static final double EMART_YANGJAE_LATITUDE = 37.463337;
    private static final double EMART_YANGJAE_LONGITUDE = 127.036085;
    //이마트 역삼
    private static final double EMART_YEUCKSAM_LATITUDE = 37.499189;
    private static final double EMART_YEUCKSAM_LONGITUDE = 127.048561;
    //이마트 영등포
    private static final double EMART_YEONGDEUNGPO_LATITUDE =37.517221;
    private static final double EMART_YEONGDEUNGPO_LONGITUDE = 126.903177;
    //이마트 신도림
    private static final double EMART_SINDORIM_LATITUDE = 37.508350;
    private static final double EMART_SINDORIM_LONGITUDE = 126.890453;
    //이마트 마포공덕
    private static final double EMART_MAPOGONGDEOCK_LATITUDE = 37.542395;
    private static final double EMART_MAPOGONGDEOCK_LONGITUDE = 126.953370;
    //이마트 광명소하
    private static final double EMART_GWANGMEONG_LATITUDE = 37.446714;
    private static final double EMART_GWANGMEONG_LONGITUDE = 126.884573;
    //이마트 구로
    private static final double EMART_GURO_LATITUDE = 37.484421;
    private static final double EMART_GURO_LONGITUDE = 126.897871;
    //이마트 왕십리
    private static final double EMART_WANGSIPRI_LATITUDE = 37.561784;
    private static final double EMART_WANGSIPRI_LONGITUDE = 127.038376;
    //이마트 자양
    private static final double EMART_JAYANG_LATITUDE = 37.538550;
    private static final double EMART_JAYANG_LONGITUDE =127.073426;
    //이마트 목동
    private static final double EMART_MOCKDONG_LATITUDE = 37.525622;
    private static final double EMART_MOCKDONG_LONGITUDE = 126.870926;
    //이마트 청계천
    private static final double EMART_CHEONGGYEACHEON_LATITUDE = 37.571344;
    private static final double EMART_CHEONGGYEACHEON_LONGITUDE = 127.022084;
    //이마트 미아
    private static final double EMART_MIA_LATITUDE = 37.611094;
    private static final double EMART_MIA_LONGITUDE = 127.029936;
    //이마트 상봉
    private static final double EMART_SANGBONG_LATITUDE = 37.596406;
    private static final double EMART_SANGBONG_LONGITUDE =127.093622;
    //이마트 하남
    private static final double EMART_HANAM_LATITUDE = 37.552831;
    private static final double EMART_HANAM_LONGITUDE =127.205390;
    //이마트 은평
    private static final double EMART_EUNPYEONG_LATITUDE = 37.600302;
    private static final double EMART_EUNPYEONG_LONGITUDE = 126.920111;
    //이마트 성남
    private static final double EMART_SEONGNAM_LATITUDE = 37.444106;
    private static final double EMART_SEONGNAM_LONGITUDE = 127.141603;
    //이마트 수색
    private static final double EMART_SUSAECK_LATITUDE = 37.579934;
    private static final double EMART_SUSAECK_LONGITUDE = 126.898474;
    //이마트 창동
    private static final double EMART_CHANGDONG_LATITUDE = 37.651700;
    private static final double EMART_CHANGDONG_LONGITUDE =127.046941;
    //이마트 월계
    private static final double EMART_WALLGYEA_LATITUDE = 37.626553;
    private static final double EMART_WALLGYEA_LONGITUDE = 127.061893;
    //이마트 부천
    private static final double EMART_BUCHEON_LATITUDE = 37.484039;
    private static final double EMART_BUCHEON_LONGITUDE = 126.782769;
    //이마트 명일
    private static final double EMART_MYUNGIL_LATITUDE = 37.554596;
    private static final double EMART_MYUNGIL_LONGITUDE = 127.155951;
    //이마트 진접
    private static final double EMART_JINJUB_LATITUDE = 37.708798;
    private static final double EMART_JINJUB_LONGITUDE = 127.181973;
    //이마트 화정
    private static final double EMART_HWAJEONG_LATITUDE = 37.632595;
    private static final double EMART_HWAJEONG_LONGITUDE = 126.833313;
    //이마트 가든파이브
    private static final double EMART_GARDENFIVE_LATITUDE = 37.478915;
    private static final double EMART_GARDENFIVE_LONGITUDE = 127.118455;
    //이마트 계양
    private static final double EMART_GYEAYANG_LATITUDE = 37.530980;
    private static final double EMART_GYEAYANG_LONGITUDE = 126.736878;
    //이마트 의정부
    private static final double EMART_UIJEONGBU_LATITUDE = 37.743034;
    private static final double EMART_UIJEONGBU_LONGITUDE =127.102313;
    //이마트 일산
    private static final double EMART_ILSAN_LATITUDE = 37.648244;
    private static final double EMART_ILSAN_LONGITUDE = 126.781903;
    //이마트 중동
    private static final double EMART_JOONGDONG_LATITUDE = 37.504039;
    private static final double EMART_JOONGDONG_LONGITUDE =126.763910;
    //이마트 부평
    private static final double EMART_BUPYEONG_LATITUDE = 37.516382;
    private static final double EMART_BUPYEONG_LONGITUDE = 126.725951;


    //////////////////////////////LOTTEMART//////////////////////////////
    //롯데마트 의왕점
    private static final double LOTTEMART_UIWANG_LATITUDE = 37.380483;
    private static final double LOTTEMART_UIWANG_LONGITUDE = 126.972831;
    //롯데마트 청량리점
    private static final double LOTTEMART_CHEONGNYANGNI_LATITUDE = 37.584023;
    private static final double LOTTEMART_CHEONGNYANGNI_LONGITUDE = 127.048280;
    //롯데마트 평촌점
    private static final double LOTTEMART_PYEONGCHON_LATITUDE = 37.388519;
    private static final double LOTTEMART_PYEONGCHON_LONGITUDE = 126.950091;
    //롯데마트 천천점
    private static final double LOTTEMART_CHEONCHEON_LATITUDE = 37.303076 ;
    private static final double LOTTEMART_CHEONCHEON_LONGITUDE = 126.984546;
    //롯데마트 수지점
    private static final double LOTTEMART_SUJI_LATITUDE = 37.326908 ;
    private static final double LOTTEMART_SUJI_LONGITUDE = 127.101276;
    //롯데마트 부평역점
    private static final double LOTTEMART_BUPYEONGSTATION_LATITUDE = 37.495902 ;
    private static final double LOTTEMART_BUPYEONGSTATION_LONGITUDE = 126.721374;
    //롯데마트 권선점
    private static final double LOTTEMART_KWONSEON_LATITUDE = 37.257562 ;
    private static final double LOTTEMART_KWONSEON_LONGITUDE = 127.033534;
    //롯데마트 시화점
    private static final double LOTTEMART_SIHWA_LATITUDE = 37.343184 ;
    private static final double LOTTEMART_SIHWA_LONGITUDE = 126.731199;
    //롯데마트 시흥점
    private static final double LOTTEMART_SIHEUNG_LATITUDE = 37.449164 ;
    private static final double LOTTEMART_SIHEUNG_LONGITUDE = 126.792621;
    //롯데마트 서현점
    private static final double LOTTEMART_SEOHYEOUN_LATITUDE = 37.392981 ;
    private static final double LOTTEMART_SEOHYEOUN_LONGITUDE = 127.122361;
    //롯데마트 와스타디움점
    private static final double LOTTEMART_WASTADIUM_LATITUDE = 37.333498 ;
    private static final double LOTTEMART_WASTADIUM_LONGITUDE = 126.818494;
    //롯데마트 잠실점
    private static final double LOTTEMART_JAMSIL_LATITUDE = 37.528552 ;
    private static final double LOTTEMART_JAMSIL_LONGITUDE = 127.094405;
    //롯데마트 서울역점
    private static final double LOTTEMART_SEOULSTATION_LATITUDE = 37.570750 ;
    private static final double LOTTEMART_SEOULSTATION_LONGITUDE = 126.968349;
    //롯데마트 신갈점
    private static final double LOTTEMART_SINGAL_LATITUDE = 37.288814 ;
    private static final double LOTTEMART_SINGAL_LONGITUDE = 127.095027;
    //롯데마트 송파점
    private static final double LOTTEMART_SONGPA_LATITUDE = 37.507162 ;
    private static final double LOTTEMART_SONGPA_LONGITUDE = 127.116177;
    //롯데마트 판교점
    private static final double LOTTEMART_PANGYO_LATITUDE = 37.409548 ;
    private static final double LOTTEMART_PANGYO_LONGITUDE = 127.111185;
    //롯데마트 삼산점
    private static final double LOTTEMART_SAMSAN_LATITUDE = 37.524280 ;
    private static final double LOTTEMART_SAMSAN_LONGITUDE = 126.726750;
    //롯데마트 상록점
    private static final double LOTTEMART_SANGROCK_LATITUDE = 37.309104 ;
    private static final double LOTTEMART_SANGROCK_LONGITUDE = 126.860375;
    //롯데마트 행당역점
    private static final double LOTTEMART_HANGDANGSTATION_LATITUDE = 37.573054 ;
    private static final double LOTTEMART_HANGDANGSTATION_LONGITUDE = 127.025390;
    //롯데마트 강변점
    private static final double LOTTEMART_KANGBYEON_LATITUDE = 37.552273 ;
    private static final double LOTTEMART_KANGBYEON_LONGITUDE = 127.092805;
    //롯데마트 화정점
    private static final double LOTTEMART_HWAJEONG_LATITUDE = 37.659127 ;
    private static final double LOTTEMART_HWAJEONG_LONGITUDE = 126.817560;
    //롯데마트 김포공항점
    private static final double LOTTEMART_KIMPOAIRPORT_LATITUDE = 37.588916 ;
    private static final double LOTTEMART_KIMPOAIRPORT_LONGITUDE = 126.798828;
    //롯데마트 안성여행센터
    private static final double LOTTEMART_ANSUNGTRAVLE_LATITUDE = 37.000467 ;
    private static final double LOTTEMART_ANSUNGTRAVLE_LONGITUDE = 126.923056;
    //롯데마트 안성점
    private static final double LOTTEMART_ANSUNG_LATITUDE = 37.021401 ;
    private static final double LOTTEMART_ANSUNG_LONGITUDE = 127.200214;
    //롯데마트 평택점
    private static final double LOTTEMART_PYEONGTACK_LATITUDE = 37.007941;
    private static final double LOTTEMART_PYEONGTACK_LONGITUDE = 127.111575;
    //롯데마트 오산점
    private static final double LOTTEMART_OSAN_LATITUDE = 37.161819;
    private static final double LOTTEMART_OSAN_LONGITUDE = 127.069751;
    //롯데마트 중계점
    private static final double LOTTEMART_JOONGGYEA_LATITUDE = 37.661600 ;
    private static final double LOTTEMART_JOONGGYEA_LONGITUDE = 127.068182;
    //롯데마트 동두천점
    private static final double LOTTEMART_DONGDUCHEON_LATITUDE = 37.893052 ;
    private static final double LOTTEMART_DONGDUCHEON_LONGITUDE = 127.049734;
    //롯데마트 고양점
    private static final double LOTTEMART_GOYANG_LATITUDE = 37.635896 ;
    private static final double LOTTEMART_GOYANG_LONGITUDE = 126.833792;
    //롯데마트 주엽점
    private static final double LOTTEMART_JUYEUP_LATITUDE = 37.686301 ;
    private static final double LOTTEMART_JUYEUP_LONGITUDE = 126.753894;
    //롯데마트 김포점
    private static final double LOTTEMART_KIMPO_LATITUDE = 37.671976 ;
    private static final double LOTTEMART_KIMPO_LONGITUDE = 126.670872;
    //롯데마트 검단점
    private static final double LOTTEMART_KUMDAN_LATITUDE = 37.601047 ;
    private static final double LOTTEMART_KUMDAN_LONGITUDE = 126.662806;
    //롯데마트 청라점
    private static final double LOTTEMART_CHUNGRA_LATITUDE = 37.564439 ;
    private static final double LOTTEMART_CHUNGRA_LONGITUDE = 126.669985;
    //롯데마트 송도점
    private static final double LOTTEMART_SONGDO_LATITUDE = 37.395027 ;
    private static final double LOTTEMART_SONGDO_LONGITUDE = 126.643769;
    //롯데마트 삼양점
    private static final double LOTTEMART_SAMYANG_LATITUDE = 37.629150 ;
    private static final double LOTTEMART_SAMYANG_LONGITUDE = 127.018004;
    //롯데마트 마석점
    private static final double LOTTEMART_MASUCK_LATITUDE = 37.654483 ;
    private static final double LOTTEMART_MASUCK_LONGITUDE = 127.308424;
    //롯데마트 양주점
    private static final double LOTTEMART_YANGJU_LATITUDE = 37.831013 ;
    private static final double LOTTEMART_YANGJU_LONGITUDE = 127.052246;


    ///////////////////////////////HOMEPLUS///////////////////////////////
    //홈플러스 안양점
    private static final double HOMEPLUS_ANYANG_LATITUDE = 37.370207;
    private static final double HOMEPLUS_ANYANG_LONGITUDE = 126.952947;
    //홈플러스 김포점
    private static final double HOMEPLUS_KIMPO_LATITUDE = 37.640025;
    private static final double HOMEPLUS_KIMPO_LONGITUDE = 126.695323;
    //홈플러스 인천청라점
    private static final double HOMEPLUS_INCHEONCHEONGRA_LATITUDE = 37.556445;
    private static final double HOMEPLUS_INCHEONCHEONGRA_LONGITUDE = 126.626658;
    //홈플러스 일산점
    private static final double HOMEPLUS_ILSAN_LATITUDE = 37.671961;
    private static final double HOMEPLUS_ILSAN_LONGITUDE = 126.768220;
    //홈플러스 계산점
    private static final double HOMEPLUS_GYESAN_LATITUDE = 37.556445;
    private static final double HOMEPLUS_GYESAN_LONGITUDE = 126.730905;
    //홈플러스 합정점
    private static final double HOMEPLUS_HAPJEONG_LATITUDE = 37.564819;
    private static final double HOMEPLUS_HAPJEONG_LONGITUDE = 126.910518;
    //홈플러스 강서점
    private static final double HOMEPLUS_GANGSEO_LATITUDE = 37.572243;
    private static final double HOMEPLUS_GANGSEO_LONGITUDE = 126.853094;
    //홈플러스 목동점
    private static final double HOMEPLUS_MOCKDONG_LATITUDE = 37.541564;
    private static final double HOMEPLUS_MOCKDONG_LONGITUDE = 126.873069;
    //홈플러스 영등포점
    private static final double HOMEPLUS_YEONGDEUNGPO_LATITUDE = 37.529683;
    private static final double HOMEPLUS_YEONGDEUNGPO_LONGITUDE = 126.896786;
    //홈플러스 고양터미널점
    private static final double HOMEPLUS_GOYANGTERMINEL_LATITUDE = 37.658773;
    private static final double HOMEPLUS_GOYANGTERMINEL_LONGITUDE = 126.790047;
    //홈플러스 작전점
    private static final double HOMEPLUS_JACKJEON_LATITUDE = 37.542089;
    private static final double HOMEPLUS_JACKJEON_LONGITUDE = 126.714679;
    //홈플러스 가좌점
    private static final double HOMEPLUS_GAJWA_LATITUDE = 37.515858;
    private static final double HOMEPLUS_GAJWA_LONGITUDE = 126.668482;
    //홈플러스 인천숭의점
    private static final double HOMEPLUS_INCHEONSUNGUI_LATITUDE = 37.488215;
    private static final double HOMEPLUS_INCHEONSUNGUI_LONGITUDE = 126.637122;
    //홈플러스 인천연수점
    private static final double HOMEPLUS_INCHEINYEONSU_LATITUDE = 37.420309;
    private static final double HOMEPLUS_INCHEINYEONSU_LONGITUDE = 126.682562;
    //홈플러스 부천상동점
    private static final double HOMEPLUS_BUCHEONSANGDONG_LATITUDE = 37.520387;
    private static final double HOMEPLUS_BUCHEONSANGDONG_LONGITUDE = 126.754350;
    //홈플러스 간석점
    private static final double HOMEPLUS_KANSUCK_LATITUDE = 37.483737;
    private static final double HOMEPLUS_KANSUCK_LONGITUDE = 126.688185;
    //홈플러스 구월점
    private static final double HOMEPLUS_GUWALL_LATITUDE = 37.467885;
    private static final double HOMEPLUS_GUWALL_LONGITUDE = 126.696295;
    //홈플러스 중동점
    private static final double HOMEPLUS_JOONGDONG_LATITUDE = 37.516918;
    private static final double HOMEPLUS_JOONGDONG_LONGITUDE = 126.774325;
    //홈플러스 월드컵점
    private static final double HOMEPLUS_WORLDCUP_LATITUDE = 37.582666;
    private static final double HOMEPLUS_WORLDCUP_LONGITUDE = 126.892582;
    //홈플러스 인하점
    private static final double HOMEPLUS_INHA_LATITUDE = 37.465115;
    private static final double HOMEPLUS_INHA_LONGITUDE = 126.643773;
    //홈플러스 인천논현점
    private static final double HOMEPLUS_INCHEONNONHYEON_LATITUDE = 37.420018;
    private static final double HOMEPLUS_INCHEONNONHYEON_LONGITUDE = 126.723054;
    //홈플러스 가양점
    private static final double HOMEPLUS_GAYANG_LATITUDE = 37.575027;
    private static final double HOMEPLUS_GAYANG_LONGITUDE = 126.849767;
    //홈플러스 부천 소사점
    private static final double HOMEPLUS_BUCHEONSOSA_LATITUDE = 37.499295;
    private static final double HOMEPLUS_BUCHEONSOSA_LONGITUDE = 126.814185;
    //홈플러스 파주문산점
    private static final double HOMEPLUS_PAJUMUNSAN_LATITUDE = 37.880653;
    private static final double HOMEPLUS_PAJUMUNSAN_LONGITUDE = 126.788558;
    //홈플러스 평촌점
    private static final double HOMEPLUS_PYEONGCHON_LATITUDE = 37.393219;
    private static final double HOMEPLUS_PYEONGCHON_LONGITUDE = 126.950416;
    //홈플러스 안산점
    private static final double HOMEPLUS_ANSAN_LATITUDE = 37.324107;
    private static final double HOMEPLUS_ANSAN_LONGITUDE = 126.850400;
    //홈플러스 서수원점
    private static final double HOMEPLUS_WESTSUWON_LATITUDE = 37.282646;
    private static final double HOMEPLUS_WESTSUWON_LONGITUDE = 126.954647;
    //홈플러스 안산고잔점
    private static final double HOMEPLUS_ANSANGOJAN_LATITUDE = 37.317925;
    private static final double HOMEPLUS_ANSANGOJAN_LONGITUDE = 126.828987;
    //홈플러스 안산선부점
    private static final double HOMEPLUS_ANSANSEONBU_LATITUDE = 37.338774;
    private static final double HOMEPLUS_ANSANSEONBU_LONGITUDE = 126.810884;
    //홈플러스 시흥점
    private static final double HOMEPLUS_SIHEUNG_LATITUDE = 37.459251;
    private static final double HOMEPLUS_SIHEUNG_LONGITUDE = 126.900653;
    //홈플러스 동수원점
    private static final double HOMEPLUS_EASTSUWON_LATITUDE = 37.268986;
    private static final double HOMEPLUS_EASTSUWON_LONGITUDE = 127.027991;
    //홈플러스 금천점
    private static final double HOMEPLUS_GEUMCHEON_LATITUDE = 37.475168;
    private static final double HOMEPLUS_GEUMCHEON_LONGITUDE = 126.897910;
    //홈플러스 영통점
    private static final double HOMEPLUS_YEONGTONG_LATITUDE = 37.261530;
    private static final double HOMEPLUS_YEONGTONG_LONGITUDE = 127.071979;
    //홈플러스 야탑점
    private static final double HOMEPLUS_YATOP_LATITUDE = 37.421113;
    private static final double HOMEPLUS_YATOP_LONGITUDE = 127.125011 ;
    //홈플러스 원천점
    private static final double HOMEPLUS_WONCHEON_LATITUDE = 37.285165;
    private static final double HOMEPLUS_WONCHEON_LONGITUDE = 127.064099;
    //홈플러스 북수원점
    private static final double HOMEPLUS_NORTHSUWON_LATITUDE = 37.319427;
    private static final double HOMEPLUS_NORTHSUWON_LONGITUDE = 127.006668;
    //홈플러스 서울남현점
    private static final double HOMEPLUS_SEOULNAMHYEON_LATITUDE = 37.487526;
    private static final double HOMEPLUS_SEOULNAMHYEON_LONGITUDE = 126.979827;
    //홈플러스 신도림점
    private static final double HOMEPLUS_SINDORIM_LATITUDE = 37.523177;
    private static final double HOMEPLUS_SINDORIM_LONGITUDE = 126.890563;
    //홈플러스 병점점
    private static final double HOMEPLUS_BYEONGJEOM_LATITUDE = 37.219081;
    private static final double HOMEPLUS_BYEONGJEOM_LONGITUDE = 127.032884;


    ////////////////////////////배열초기화//////////////////////////
    public static void InitializeMart() {
        //마트의 위도, 경도 배열
        MART_NAME = new ArrayList<String>();
        MART_LATITUDE = new ArrayList<Double>();
        MART_LONGITUDE = new ArrayList<Double>();

        //EMART
        MART_NAME.add("이마트 안양점");
        MART_LATITUDE.add(EMART_ANYANG_LATITUDE);
        MART_LONGITUDE.add(EMART_ANYANG_LONGITUDE);

        MART_NAME.add("이마트 산본점");
        MART_LATITUDE.add(EMART_SANBON_LATITUDE);
        MART_LONGITUDE.add(EMART_SANBON_LONGITUDE);

        MART_NAME.add("이마트 춘천점");
        MART_LATITUDE.add(EMART_CHUNCHEON_LATITUDE);
        MART_LONGITUDE.add(EMART_CHUNCHEON_LONGITUDE);

        MART_NAME.add("이마트 이수점");
        MART_LATITUDE.add(EMART_ISU_LATITUDE);
        MART_LONGITUDE.add(EMART_ISU_LONGITUDE);

        MART_NAME.add("이마트 용산점");
        MART_LATITUDE.add(EMART_YONGSAN_LATITUDE);
        MART_LONGITUDE.add(EMART_YONGSAN_LONGITUDE);

        MART_NAME.add("이마트 여의도점");
        MART_LATITUDE.add(EMART_YEOUIDO_LATITUDE);
        MART_LONGITUDE.add(EMART_YEOUIDO_LONGITUDE);

        MART_NAME.add("이마트 양재점");
        MART_LATITUDE.add(EMART_YANGJAE_LATITUDE);
        MART_LONGITUDE.add(EMART_YANGJAE_LONGITUDE);

        MART_NAME.add("이마트 역삼점");
        MART_LATITUDE.add(EMART_YEUCKSAM_LATITUDE);
        MART_LONGITUDE.add(EMART_YEUCKSAM_LONGITUDE);

        MART_NAME.add("이마트 영등포점");
        MART_LATITUDE.add(EMART_YEONGDEUNGPO_LATITUDE);
        MART_LONGITUDE.add(EMART_YEONGDEUNGPO_LONGITUDE);

        MART_NAME.add("이마트 신도림점");
        MART_LATITUDE.add(EMART_SINDORIM_LATITUDE);
        MART_LONGITUDE.add(EMART_SINDORIM_LONGITUDE);

        MART_NAME.add("이마트 마포공덕점");
        MART_LATITUDE.add(EMART_MAPOGONGDEOCK_LATITUDE);
        MART_LONGITUDE.add(EMART_MAPOGONGDEOCK_LONGITUDE);

        MART_NAME.add("이마트 광명점");
        MART_LATITUDE.add(EMART_GWANGMEONG_LATITUDE);
        MART_LONGITUDE.add(EMART_GWANGMEONG_LONGITUDE);

        MART_NAME.add("이마트 구로점");
        MART_LATITUDE.add(EMART_GURO_LATITUDE);
        MART_LONGITUDE.add(EMART_GURO_LONGITUDE);

        MART_NAME.add("이마트 왕십리점");
        MART_LATITUDE.add(EMART_WANGSIPRI_LATITUDE);
        MART_LONGITUDE.add(EMART_WANGSIPRI_LONGITUDE);

        MART_NAME.add("이마트 자양점");
        MART_LATITUDE.add(EMART_JAYANG_LATITUDE);
        MART_LONGITUDE.add(EMART_JAYANG_LONGITUDE);

        MART_NAME.add("이마트 목동점");
        MART_LATITUDE.add(EMART_MOCKDONG_LATITUDE);
        MART_LONGITUDE.add(EMART_MOCKDONG_LONGITUDE);

        MART_NAME.add("이마트 청계천점");
        MART_LATITUDE.add(EMART_CHEONGGYEACHEON_LATITUDE);
        MART_LONGITUDE.add(EMART_CHEONGGYEACHEON_LONGITUDE);

        MART_NAME.add("이마트 미아점");
        MART_LATITUDE.add(EMART_MIA_LATITUDE);
        MART_LONGITUDE.add(EMART_MIA_LONGITUDE);

        MART_NAME.add("이마트 상봉점");
        MART_LATITUDE.add(EMART_SANGBONG_LATITUDE);
        MART_LONGITUDE.add(EMART_SANGBONG_LONGITUDE);

        MART_NAME.add("이마트 하남점");
        MART_LATITUDE.add(EMART_HANAM_LATITUDE);
        MART_LONGITUDE.add(EMART_HANAM_LONGITUDE);

        MART_NAME.add("이마트 은평점");
        MART_LATITUDE.add(EMART_EUNPYEONG_LATITUDE);
        MART_LONGITUDE.add(EMART_EUNPYEONG_LONGITUDE);

        MART_NAME.add("이마트 성남점");
        MART_LATITUDE.add(EMART_SEONGNAM_LATITUDE);
        MART_LONGITUDE.add(EMART_SEONGNAM_LONGITUDE);

        MART_NAME.add("이마트 수색점");
        MART_LATITUDE.add(EMART_SUSAECK_LATITUDE);
        MART_LONGITUDE.add(EMART_SUSAECK_LONGITUDE);

        MART_NAME.add("이마트 창동점");
        MART_LATITUDE.add(EMART_CHANGDONG_LATITUDE);
        MART_LONGITUDE.add(EMART_CHANGDONG_LONGITUDE);

        MART_NAME.add("이마트 월계점");
        MART_LATITUDE.add(EMART_WALLGYEA_LATITUDE);
        MART_LONGITUDE.add(EMART_WALLGYEA_LONGITUDE);

        MART_NAME.add("이마트 부천점");
        MART_LATITUDE.add(EMART_BUCHEON_LATITUDE);
        MART_LONGITUDE.add(EMART_BUCHEON_LONGITUDE);

        MART_NAME.add("이마트 명일점");
        MART_LATITUDE.add(EMART_MYUNGIL_LATITUDE);
        MART_LONGITUDE.add(EMART_MYUNGIL_LONGITUDE);

        MART_NAME.add("이마트 진접점");
        MART_LATITUDE.add(EMART_JINJUB_LATITUDE);
        MART_LONGITUDE.add(EMART_JINJUB_LONGITUDE);

        MART_NAME.add("이마트 화정점");
        MART_LATITUDE.add(EMART_HWAJEONG_LATITUDE);
        MART_LONGITUDE.add(EMART_HWAJEONG_LONGITUDE);

        MART_NAME.add("이마트 가든파이브점");
        MART_LATITUDE.add(EMART_GARDENFIVE_LATITUDE);
        MART_LONGITUDE.add(EMART_GARDENFIVE_LONGITUDE);

        MART_NAME.add("이마트 계양점");
        MART_LATITUDE.add(EMART_GYEAYANG_LATITUDE);
        MART_LONGITUDE.add(EMART_GYEAYANG_LONGITUDE);

        MART_NAME.add("이마트 의정부점");
        MART_LATITUDE.add(EMART_UIJEONGBU_LATITUDE);
        MART_LONGITUDE.add(EMART_UIJEONGBU_LONGITUDE);

        MART_NAME.add("이마트 일산점");
        MART_LATITUDE.add(EMART_ILSAN_LATITUDE);
        MART_LONGITUDE.add(EMART_ILSAN_LONGITUDE);

        MART_NAME.add("이마트 중동점");
        MART_LATITUDE.add(EMART_JOONGDONG_LATITUDE);
        MART_LONGITUDE.add(EMART_JOONGDONG_LONGITUDE);

        MART_NAME.add("이마트 부평점");
        MART_LATITUDE.add(EMART_BUPYEONG_LATITUDE);
        MART_LONGITUDE.add(EMART_BUPYEONG_LONGITUDE);


        //LOTTEMART
        MART_NAME.add("롯데마트 의왕점");
        MART_LATITUDE.add(LOTTEMART_UIWANG_LATITUDE);
        MART_LONGITUDE.add(LOTTEMART_UIWANG_LONGITUDE);

        MART_NAME.add("롯데마트 청량리점");
        MART_LATITUDE.add(LOTTEMART_CHEONGNYANGNI_LATITUDE);
        MART_LONGITUDE.add(LOTTEMART_CHEONGNYANGNI_LONGITUDE);

        MART_NAME.add("롯데마트 평촌점");
        MART_LATITUDE.add(LOTTEMART_PYEONGCHON_LATITUDE);
        MART_LONGITUDE.add(LOTTEMART_PYEONGCHON_LONGITUDE);

        MART_NAME.add("롯데마트 천천점");
        MART_LATITUDE.add(LOTTEMART_CHEONCHEON_LATITUDE);
        MART_LONGITUDE.add(LOTTEMART_CHEONCHEON_LONGITUDE);

        MART_NAME.add("롯데마트 수지점");
        MART_LATITUDE.add(LOTTEMART_SUJI_LATITUDE);
        MART_LONGITUDE.add(LOTTEMART_SUJI_LONGITUDE);

        MART_NAME.add("롯데마트 부평역점");
        MART_LATITUDE.add(LOTTEMART_BUPYEONGSTATION_LATITUDE);
        MART_LONGITUDE.add(LOTTEMART_BUPYEONGSTATION_LONGITUDE);

        MART_NAME.add("롯데마트 권선점");
        MART_LATITUDE.add(LOTTEMART_KWONSEON_LATITUDE);
        MART_LONGITUDE.add(LOTTEMART_KWONSEON_LONGITUDE);

        MART_NAME.add("롯데마트 시화점");
        MART_LATITUDE.add(LOTTEMART_SIHWA_LATITUDE);
        MART_LONGITUDE.add(LOTTEMART_SIHWA_LONGITUDE);

        MART_NAME.add("롯데마트 시흥점");
        MART_LATITUDE.add(LOTTEMART_SIHEUNG_LATITUDE);
        MART_LONGITUDE.add(LOTTEMART_SIHEUNG_LONGITUDE);

        MART_NAME.add("롯데마트 서현점");
        MART_LATITUDE.add(LOTTEMART_SEOHYEOUN_LATITUDE);
        MART_LONGITUDE.add(LOTTEMART_SEOHYEOUN_LONGITUDE);

        MART_NAME.add("롯데마트 와스타디움점");
        MART_LATITUDE.add(LOTTEMART_WASTADIUM_LATITUDE);
        MART_LONGITUDE.add(LOTTEMART_WASTADIUM_LONGITUDE);

        MART_NAME.add("롯데마트 잠실점");
        MART_LATITUDE.add(LOTTEMART_JAMSIL_LATITUDE);
        MART_LONGITUDE.add(LOTTEMART_JAMSIL_LONGITUDE);

        MART_NAME.add("롯데마트 서울역점");
        MART_LATITUDE.add(LOTTEMART_SEOULSTATION_LATITUDE);
        MART_LONGITUDE.add(LOTTEMART_SEOULSTATION_LONGITUDE);

        MART_NAME.add("롯데마트 신갈점");
        MART_LATITUDE.add(LOTTEMART_SINGAL_LATITUDE);
        MART_LONGITUDE.add(LOTTEMART_SINGAL_LONGITUDE);

        MART_NAME.add("롯데마트 송파점");
        MART_LATITUDE.add(LOTTEMART_SONGPA_LATITUDE);
        MART_LONGITUDE.add(LOTTEMART_SONGPA_LONGITUDE);

        MART_NAME.add("롯데마트 판교점");
        MART_LATITUDE.add(LOTTEMART_PANGYO_LATITUDE);
        MART_LONGITUDE.add(LOTTEMART_PANGYO_LONGITUDE);

        MART_NAME.add("롯데마트 삼산점");
        MART_LATITUDE.add(LOTTEMART_SAMSAN_LATITUDE);
        MART_LONGITUDE.add(LOTTEMART_SAMSAN_LONGITUDE);

        MART_NAME.add("롯데마트 상록점");
        MART_LATITUDE.add(LOTTEMART_SANGROCK_LATITUDE);
        MART_LONGITUDE.add(LOTTEMART_SANGROCK_LONGITUDE);

        MART_NAME.add("롯데마트 행단역점");
        MART_LATITUDE.add(LOTTEMART_HANGDANGSTATION_LATITUDE);
        MART_LONGITUDE.add(LOTTEMART_HANGDANGSTATION_LONGITUDE);

        MART_NAME.add("롯데마트 강변점");
        MART_LATITUDE.add(LOTTEMART_KANGBYEON_LATITUDE);
        MART_LONGITUDE.add(LOTTEMART_KANGBYEON_LONGITUDE);

        MART_NAME.add("롯데마트 화정점");
        MART_LATITUDE.add(LOTTEMART_HWAJEONG_LATITUDE);
        MART_LONGITUDE.add(LOTTEMART_HWAJEONG_LONGITUDE);

        MART_NAME.add("롯데마트 김포공항점");
        MART_LATITUDE.add(LOTTEMART_KIMPOAIRPORT_LATITUDE);
        MART_LONGITUDE.add(LOTTEMART_KIMPOAIRPORT_LONGITUDE);

        MART_NAME.add("롯데마트 안성여행센터점");
        MART_LATITUDE.add(LOTTEMART_ANSUNGTRAVLE_LATITUDE);
        MART_LONGITUDE.add(LOTTEMART_ANSUNGTRAVLE_LONGITUDE);

        MART_NAME.add("롯데마트 안성점");
        MART_LATITUDE.add(LOTTEMART_ANSUNG_LATITUDE);
        MART_LONGITUDE.add(LOTTEMART_ANSUNG_LONGITUDE);

        MART_NAME.add("롯데마트 평택점");
        MART_LATITUDE.add(LOTTEMART_PYEONGTACK_LATITUDE);
        MART_LONGITUDE.add(LOTTEMART_PYEONGTACK_LONGITUDE);

        MART_NAME.add("롯데마트 오산점");
        MART_LATITUDE.add(LOTTEMART_OSAN_LATITUDE);
        MART_LONGITUDE.add(LOTTEMART_OSAN_LONGITUDE);

        MART_NAME.add("롯데마트 중계점");
        MART_LATITUDE.add(LOTTEMART_JOONGGYEA_LATITUDE);
        MART_LONGITUDE.add(LOTTEMART_JOONGGYEA_LONGITUDE);

        MART_NAME.add("롯데마트 동두천점");
        MART_LATITUDE.add(LOTTEMART_DONGDUCHEON_LATITUDE);
        MART_LONGITUDE.add(LOTTEMART_DONGDUCHEON_LONGITUDE);

        MART_NAME.add("롯데마트 고양점");
        MART_LATITUDE.add(LOTTEMART_GOYANG_LATITUDE);
        MART_LONGITUDE.add(LOTTEMART_GOYANG_LONGITUDE);

        MART_NAME.add("롯데마트 주엽점");
        MART_LATITUDE.add(LOTTEMART_JUYEUP_LATITUDE);
        MART_LONGITUDE.add(LOTTEMART_JUYEUP_LONGITUDE);

        MART_NAME.add("롯데마트 김포점");
        MART_LATITUDE.add(LOTTEMART_KIMPO_LATITUDE);
        MART_LONGITUDE.add(LOTTEMART_KIMPO_LONGITUDE);

        MART_NAME.add("롯데마트 검단점");
        MART_LATITUDE.add(LOTTEMART_KUMDAN_LATITUDE);
        MART_LONGITUDE.add(LOTTEMART_KUMDAN_LONGITUDE);

        MART_NAME.add("롯데마트 청라점");
        MART_LATITUDE.add(LOTTEMART_CHUNGRA_LATITUDE);
        MART_LONGITUDE.add(LOTTEMART_CHUNGRA_LONGITUDE);

        MART_NAME.add("롯데마트 송도점");
        MART_LATITUDE.add(LOTTEMART_SONGDO_LATITUDE);
        MART_LONGITUDE.add(LOTTEMART_SONGDO_LONGITUDE);

        MART_NAME.add("롯데마트 삼양점");
        MART_LATITUDE.add(LOTTEMART_SAMYANG_LATITUDE);
        MART_LONGITUDE.add(LOTTEMART_SAMYANG_LONGITUDE);

        MART_NAME.add("롯데마트 마석점");
        MART_LATITUDE.add(LOTTEMART_MASUCK_LATITUDE);
        MART_LONGITUDE.add(LOTTEMART_MASUCK_LONGITUDE);

        MART_NAME.add("롯데마트 양주점");
        MART_LATITUDE.add(LOTTEMART_YANGJU_LATITUDE);
        MART_LONGITUDE.add(LOTTEMART_YANGJU_LONGITUDE);


        //HOMEPLUS
        MART_NAME.add("홈플러스 안양점");
        MART_LATITUDE.add(HOMEPLUS_ANYANG_LATITUDE);
        MART_LONGITUDE.add(HOMEPLUS_ANYANG_LONGITUDE);

        MART_NAME.add("홈플러스 김포점");
        MART_LATITUDE.add(HOMEPLUS_KIMPO_LATITUDE);
        MART_LONGITUDE.add(HOMEPLUS_KIMPO_LONGITUDE);

        MART_NAME.add("홈플러스 인천청라점");
        MART_LATITUDE.add(HOMEPLUS_INCHEONCHEONGRA_LATITUDE);
        MART_LONGITUDE.add(HOMEPLUS_INCHEONCHEONGRA_LONGITUDE);

        MART_NAME.add("홈플러스 일산점");
        MART_LATITUDE.add(HOMEPLUS_ILSAN_LATITUDE);
        MART_LONGITUDE.add(HOMEPLUS_ILSAN_LONGITUDE);

        MART_NAME.add("홈플러스 계산점");
        MART_LATITUDE.add(HOMEPLUS_GYESAN_LATITUDE);
        MART_LONGITUDE.add(HOMEPLUS_GYESAN_LONGITUDE);

        MART_NAME.add("홈플러스 합정점");
        MART_LATITUDE.add(HOMEPLUS_HAPJEONG_LATITUDE);
        MART_LONGITUDE.add(HOMEPLUS_HAPJEONG_LONGITUDE);

        MART_NAME.add("홈플러스 강서점");
        MART_LATITUDE.add(HOMEPLUS_GANGSEO_LATITUDE);
        MART_LONGITUDE.add(HOMEPLUS_GANGSEO_LONGITUDE);

        MART_NAME.add("홈플러스 목동점");
        MART_LATITUDE.add(HOMEPLUS_MOCKDONG_LATITUDE);
        MART_LONGITUDE.add(HOMEPLUS_MOCKDONG_LONGITUDE);

        MART_NAME.add("홈플러스 영등포점");
        MART_LATITUDE.add(HOMEPLUS_YEONGDEUNGPO_LATITUDE);
        MART_LONGITUDE.add(HOMEPLUS_YEONGDEUNGPO_LONGITUDE);

        MART_NAME.add("홈플러스 고양터미널점");
        MART_LATITUDE.add(HOMEPLUS_GOYANGTERMINEL_LATITUDE);
        MART_LONGITUDE.add(HOMEPLUS_GOYANGTERMINEL_LONGITUDE);

        MART_NAME.add("홈플러스 작전점");
        MART_LATITUDE.add(HOMEPLUS_JACKJEON_LATITUDE);
        MART_LONGITUDE.add(HOMEPLUS_JACKJEON_LONGITUDE);

        MART_NAME.add("홈플러스 가좌점");
        MART_LATITUDE.add(HOMEPLUS_GAJWA_LATITUDE);
        MART_LONGITUDE.add(HOMEPLUS_GAJWA_LONGITUDE);

        MART_NAME.add("홈플러스 인천숭의점");
        MART_LATITUDE.add(HOMEPLUS_INCHEONSUNGUI_LATITUDE);
        MART_LONGITUDE.add(HOMEPLUS_INCHEONSUNGUI_LONGITUDE);

        MART_NAME.add("홈플러스 인천연수점");
        MART_LATITUDE.add(HOMEPLUS_INCHEINYEONSU_LATITUDE);
        MART_LONGITUDE.add(HOMEPLUS_INCHEINYEONSU_LONGITUDE);

        MART_NAME.add("홈플러스 부천상동점");
        MART_LATITUDE.add(HOMEPLUS_BUCHEONSANGDONG_LATITUDE);
        MART_LONGITUDE.add(HOMEPLUS_BUCHEONSANGDONG_LONGITUDE);

        MART_NAME.add("홈플러스 간석점");
        MART_LATITUDE.add(HOMEPLUS_KANSUCK_LATITUDE);
        MART_LONGITUDE.add(HOMEPLUS_KANSUCK_LONGITUDE);

        MART_NAME.add("홈플러스 구월점");
        MART_LATITUDE.add(HOMEPLUS_GUWALL_LATITUDE);
        MART_LONGITUDE.add(HOMEPLUS_GUWALL_LONGITUDE);

        MART_NAME.add("홈플러스 중동점");
        MART_LATITUDE.add(HOMEPLUS_JOONGDONG_LATITUDE);
        MART_LONGITUDE.add(HOMEPLUS_JOONGDONG_LONGITUDE);

        MART_NAME.add("홈플러스 월드컵점");
        MART_LATITUDE.add(HOMEPLUS_WORLDCUP_LATITUDE);
        MART_LONGITUDE.add(HOMEPLUS_WORLDCUP_LONGITUDE);

        MART_NAME.add("홈플러스 인하점");
        MART_LATITUDE.add(HOMEPLUS_INHA_LATITUDE);
        MART_LONGITUDE.add(HOMEPLUS_INHA_LONGITUDE);

        MART_NAME.add("홈플러스 인천논현점");
        MART_LATITUDE.add(HOMEPLUS_INCHEONNONHYEON_LATITUDE);
        MART_LONGITUDE.add(HOMEPLUS_INCHEONNONHYEON_LONGITUDE);

        MART_NAME.add("홈플러스 계양점");
        MART_LATITUDE.add(HOMEPLUS_GAYANG_LATITUDE);
        MART_LONGITUDE.add(HOMEPLUS_GAYANG_LONGITUDE);

        MART_NAME.add("홈플러스 부천소사점");
        MART_LATITUDE.add(HOMEPLUS_BUCHEONSOSA_LATITUDE);
        MART_LONGITUDE.add(HOMEPLUS_BUCHEONSOSA_LONGITUDE);

        MART_NAME.add("홈플러스 파주문산점");
        MART_LATITUDE.add(HOMEPLUS_PAJUMUNSAN_LATITUDE);
        MART_LONGITUDE.add(HOMEPLUS_PAJUMUNSAN_LONGITUDE);

        MART_NAME.add("홈플러스 평촌점");
        MART_LATITUDE.add(HOMEPLUS_PYEONGCHON_LATITUDE);
        MART_LONGITUDE.add(HOMEPLUS_PYEONGCHON_LONGITUDE);

        MART_NAME.add("홈플러스 안산점");
        MART_LATITUDE.add(HOMEPLUS_ANSAN_LATITUDE);
        MART_LONGITUDE.add(HOMEPLUS_ANSAN_LONGITUDE);

        MART_NAME.add("홈플러스 서수원점");
        MART_LATITUDE.add(HOMEPLUS_WESTSUWON_LATITUDE);
        MART_LONGITUDE.add(HOMEPLUS_WESTSUWON_LONGITUDE);

        MART_NAME.add("홈플러스 안산고잔점");
        MART_LATITUDE.add(HOMEPLUS_ANSANGOJAN_LATITUDE);
        MART_LONGITUDE.add(HOMEPLUS_ANSANGOJAN_LONGITUDE);

        MART_NAME.add("홈플러스 안산선부점");
        MART_LATITUDE.add(HOMEPLUS_ANSANSEONBU_LATITUDE);
        MART_LONGITUDE.add(HOMEPLUS_ANSANSEONBU_LONGITUDE);

        MART_NAME.add("홈플러스 시흥점");
        MART_LATITUDE.add(HOMEPLUS_SIHEUNG_LATITUDE);
        MART_LONGITUDE.add(HOMEPLUS_SIHEUNG_LONGITUDE);

        MART_NAME.add("홈플러스 동수원점");
        MART_LATITUDE.add(HOMEPLUS_EASTSUWON_LATITUDE);
        MART_LONGITUDE.add(HOMEPLUS_EASTSUWON_LONGITUDE);

        MART_NAME.add("홈플러스 금천점");
        MART_LATITUDE.add(HOMEPLUS_GEUMCHEON_LATITUDE);
        MART_LONGITUDE.add(HOMEPLUS_GEUMCHEON_LONGITUDE);

        MART_NAME.add("홈플러스 영통점");
        MART_LATITUDE.add(HOMEPLUS_YEONGTONG_LATITUDE);
        MART_LONGITUDE.add(HOMEPLUS_YEONGTONG_LONGITUDE);

        MART_NAME.add("홈플러스 야탑점");
        MART_LATITUDE.add(HOMEPLUS_YATOP_LATITUDE);
        MART_LONGITUDE.add(HOMEPLUS_YATOP_LONGITUDE);

        MART_NAME.add("홈플러스 원천점");
        MART_LATITUDE.add(HOMEPLUS_WONCHEON_LATITUDE);
        MART_LONGITUDE.add(HOMEPLUS_WONCHEON_LONGITUDE);

        MART_NAME.add("홈플러스 북수원점");
        MART_LATITUDE.add(HOMEPLUS_NORTHSUWON_LATITUDE);
        MART_LONGITUDE.add(HOMEPLUS_NORTHSUWON_LONGITUDE);

        MART_NAME.add("홈플러스 서울남현점");
        MART_LATITUDE.add(HOMEPLUS_SEOULNAMHYEON_LATITUDE);
        MART_LONGITUDE.add(HOMEPLUS_SEOULNAMHYEON_LONGITUDE);

        MART_NAME.add("홈플러스 신도림점");
        MART_LATITUDE.add(HOMEPLUS_SINDORIM_LATITUDE);
        MART_LONGITUDE.add(HOMEPLUS_SINDORIM_LONGITUDE);

        MART_NAME.add("홈플러스 병점점");
        MART_LATITUDE.add(HOMEPLUS_BYEONGJEOM_LATITUDE);
        MART_LONGITUDE.add(HOMEPLUS_BYEONGJEOM_LONGITUDE);
    }

    ////////////////////////////////////메인기능////////////////////////////////
    public static void SearchMart(String str) {
        InitializeMart();
        for (int i = 0; i < SearchActivity.searchItem.size(); ++i) {
            if (SearchActivity.searchItem.get(i).getData(3).contains(str)) {
                for (int j = 0; j < MART_NAME.size(); j++) {
                    if (SearchActivity.searchItem.get(i).getData(3).equals(MART_NAME.get(j))) {
                        Location locationA = new Location("point A");
                        locationA.setLatitude(SearchActivity.gps.getLatitude());
                        locationA.setLongitude(SearchActivity.gps.getLongitude());
                        Location locationB = new Location("point B");
                        locationB.setLatitude(MART_LATITUDE.get(j));
                        locationB.setLongitude(MART_LONGITUDE.get(j));
                        SearchActivity.dis = Math.round(locationA.distanceTo(locationB));
                        SearchActivity.searchItem.get(i).setDistance(String.valueOf(SearchActivity.dis));
                    }
                }
            }
        }
        ++SearchActivity.ySearchLocationWait;
    }


    public static void SearchMartInInfo(String str) {
        InitializeMart();
        for (int i = 0; i < MART_NAME.size(); ++i) {
            if (MART_NAME.get(i).contains(str)) {
                Log.d("마트명 : " + str,", 거리 : " + MART_LATITUDE.get(i)+", "+ MART_LONGITUDE.get(i));
                InfoActivity.yGetFromInsideLatitude = MART_LATITUDE.get(i);
                InfoActivity.yGetFromInsideLongitude =  MART_LONGITUDE.get(i);
                break;
            }
        }
    }
}
