package com.sevtinge.cemiuiler.module;

import com.sevtinge.cemiuiler.module.base.BaseModule;
import com.sevtinge.cemiuiler.module.systemui.*;
import com.sevtinge.cemiuiler.module.systemui.controlcenter.*;
import com.sevtinge.cemiuiler.module.systemui.lockscreen.*;
import com.sevtinge.cemiuiler.module.systemui.statusbar.*;

public class SystemUI extends BaseModule {

    @Override
    public void handleLoadPackage() {

        //
        initHook(new ChargeAnimationStyle(), mPrefsMap.getStringAsInt("system_ui_charge_animation_style", 0) > 0);
        initHook(new OriginChargeAnimation(), mPrefsMap.getBoolean("system_ui_origin_charge_animation"));
        initHook(new NotificationFreeform(), mPrefsMap.getBoolean("system_ui_notification_freeform"));

        //禁用蓝牙临时关闭
        initHook(new BluetoothRestrict(), mPrefsMap.getBoolean("various_disable_bluetooth_restrict"));

        //Monet
        initHook(new MonetThemeOverlay(), mPrefsMap.getBoolean("system_ui_monet_overlay_custom"));

        //状态栏图标
        initHook(new WifiNetworkIndicator(), mPrefsMap.getStringAsInt("system_ui_status_bar_icon_wifi_network_indicator", 0) > 0);
        initHook(new StatusBarIcon(), true);
        initHook(new WifiStandard(), mPrefsMap.getStringAsInt("system_ui_status_bar_icon_wifi_standard", 0) > 0);
        initHook(new BluetoothIcon(), mPrefsMap.getStringAsInt("system_ui_status_bar_icon_bluetooth", 0) != 0);
        initHook(new SelectiveHideIconForAlarmClock(), mPrefsMap.getStringAsInt("system_ui_status_bar_icon_alarm_clock", 0) == 3 && mPrefsMap.getInt("system_ui_status_bar_icon_alarm_clock_n", 0) > 0);
        initHook(new NotificationIconColumns(), mPrefsMap.getInt("system_ui_status_bar_notification_dots_maximum", 0) > 0 || mPrefsMap.getInt("system_ui_status_bar_notification_icon_maximum", 0) > 0);
        initHook(new UseNewHD(), mPrefsMap.getBoolean("system_ui_status_bar_use_new_hd"));
        initHook(new HideStatusBarBeforeScreenshot(), mPrefsMap.getBoolean("system_ui_status_bar_hide_icon"));

        //移动网络图标
        initHook(new MobileNetwork(), true);
        initHook(new BigMobileNetworkType(), false);

        //电池条指示器
        boolean isHideBatteryIcon = mPrefsMap.getBoolean("system_ui_status_bar_battery_percent") ||
                mPrefsMap.getBoolean("system_ui_status_bar_battery_percent_mark") ||
                mPrefsMap.getBoolean("system_ui_status_bar_battery_charging");
        initHook(new BatteryIcon(), isHideBatteryIcon);
        initHook(new BatteryIndicator(), mPrefsMap.getBoolean("system_ui_status_bar_battery_indicator_enable"));

        //网速指示器
        if (mPrefsMap.getBoolean("system_ui_statusbar_network_speed_enable_custom")) {
            initHook(NetworkSpeed.INSTANCE);
            initHook(NetworkSpeedUnit.INSTANCE);
            initHook(NetworkSpeedWidth.INSTANCE, mPrefsMap.getInt("system_ui_statusbar_network_speed_fixedcontent_width", 10) > 10);
        }
        initHook(NetworkSpeedSpacing.INSTANCE, mPrefsMap.getInt("system_ui_statusbar_network_speed_update_spacing", 3) != 3);
        initHook(NetworkSpeedSec.INSTANCE, mPrefsMap.getBoolean("system_ui_statusbar_network_speed_sec_unit"));
        initHook(StatusBarNoNetSpeedSep.INSTANCE, mPrefsMap.getBoolean("system_ui_status_bar_no_netspeed_separator"));

        //时钟指示器
        initHook(TimeBold.INSTANCE);
        initHook(TimeCustomization.INSTANCE);

        //电池和温度指示器
        initHook(new DisplayBatteryDetail());

        //居右显示
        boolean isWiFiAtLeft = mPrefsMap.getBoolean("system_ui_status_bar_wifi_at_left");
        boolean isMobileNetworkAtLeft = mPrefsMap.getBoolean("system_ui_status_bar_mobile_network_at_left");

        boolean isNetworkSpeedAtRight = mPrefsMap.getBoolean("system_ui_status_bar_network_speed_at_right");
        boolean isAlarmClockAtRight = mPrefsMap.getBoolean("system_ui_status_bar_alarm_clock_at_right");
        boolean isNFCAtRight = mPrefsMap.getBoolean("system_ui_status_bar_nfc_at_right");
        boolean isVolumeAtRight = mPrefsMap.getBoolean("system_ui_status_bar_volume_at_right");
        boolean isZenAtRight = mPrefsMap.getBoolean("system_ui_status_bar_zen_at_right");

        boolean isStatusBarIconAtRightEnable = isWiFiAtLeft || isMobileNetworkAtLeft || isNetworkSpeedAtRight || isAlarmClockAtRight || isNFCAtRight || isVolumeAtRight || isZenAtRight;

        initHook(new StatusBarIconPositionAdjust(), isStatusBarIconAtRightEnable);


        //实验性功能
        initHook(new SwitchControlPanel(), false);
        initHook(new StatusBarLayout(), false);
        initHook(new MiuiGxzwSize(), false);

        //控制中心
        initHook(new SmartHome(), false);
        initHook(new QSDetailBackGround(), mPrefsMap.getInt("system_control_center_qs_detail_bg", 0) > 0);
        initHook(new QSFiveGTile(), mPrefsMap.getBoolean("system_control_center_5g_tile"));
        initHook(new QSTileLabel(), mPrefsMap.getBoolean("system_control_center_qs_tile_label"));
        initHook(new QSGrid(), mPrefsMap.getInt("system_control_center_qs_rows", 4) > 4 || mPrefsMap.getInt("system_control_center_qs_columns", 4) > 4);
        initHook(HideMiPlayEntry.INSTANCE, mPrefsMap.getBoolean("system_ui_control_center_hide_mi_play_entry"));
        initHook(QSControlDetailBackgroundAlpha.INSTANCE, mPrefsMap.getInt("system_ui_control_center_control_detail_background_alpha", 255) != 255);
        initHook(FixMediaControlPanel.INSTANCE, mPrefsMap.getBoolean("system_ui_control_center_fix_media_control_panel"));

        //Actions
        initHook(new StatusBarActions());

        //Other
        boolean mSeparateVolume = mPrefsMap.getBoolean("system_framework_volume_separate_control") && mPrefsMap.getBoolean("system_framework_volume_separate_slider");
        initHook(new NotificationVolumeSeparateSlider(), mSeparateVolume);

        //锁屏
        initHook(new ScramblePIN(), mPrefsMap.getBoolean("system_ui_lock_screen_scramble_pin"));
        initHook(ClockDisplaySeconds.INSTANCE, mPrefsMap.getBoolean("system_ui_lock_screen_show_second"));
        initHook(ChargingCurrent.INSTANCE, mPrefsMap.getBoolean("system_ui_lock_screen_show_charging_current"));
        initHook(RemoveCamera.INSTANCE, mPrefsMap.getBoolean("system_ui_lock_screen_hide_camera"));
        initHook(RemoveSmartScreen.INSTANCE, mPrefsMap.getBoolean("system_ui_lock_screen_hide_smart_screen"));

        initHook(new EnableVolumeBlur(), mPrefsMap.getBoolean("system_ui_plugin_enable_volume_blur"));

        initHook(AddBlurEffectToLockScreen.INSTANCE);

        initHook(AddBlurEffectToNotificationView.INSTANCE, mPrefsMap.getBoolean("n_enable"));

    }

    /*public static void handleLoad(LoadPackageParam lpparam) {
        mLoadPackageParam = lpparam;

        initHook(new StatusBarIconHideHook(),true);
        initHook(new HDIconHideHook(), true);
        *//*initHook(new GxzwSizeHook(), true);*//*
        initHook(new NetworkTypeHook(), mPrefsMap.getStringAsInt("system_ui_status_bar_icon_mobile_network_type", 0) > 0);
        initHook(new WifiStandardHook(), mPrefsMap.getBoolean("system_ui_status_bar_icon_show_wifi_standard"));

        boolean mSeparateVolume = mPrefsMap.getBoolean("system_framework_separate_volume") && mPrefsMap.getBoolean("system_framework_separate_volume_slider");
        initHook(new NotificationVolumeDialogHook(), mSeparateVolume);
        initHook(new VolumeTimerValuesHook(), mPrefsMap.getBoolean("system_ui_volume_timer"));
        initHook(new ShouldPlayUnmuteSoundHook(), true);


        initHook(new ChargeAnimationStyleHook(), mPrefsMap.getStringAsInt("system_ui_charge_animation_style",0) > 0);

        initHook(new ClockCenterHook(), false);
        initHook(new GlobalActions(), true);

        initHook(new QSLabelsHook(), mPrefsMap.getBoolean("system_ui_qs_label"));


        initHook(new MiuiGxzwHook(),false);
        initHook(new StatusBarLayout(),false);
    }*/
}
