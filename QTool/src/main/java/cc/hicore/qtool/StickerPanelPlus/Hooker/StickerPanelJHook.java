package cc.hicore.qtool.StickerPanelPlus.Hooker;


import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.io.File;
import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import cc.hicore.HookItemLoader.Annotations.MethodScanner;
import cc.hicore.HookItemLoader.Annotations.UIItem;
import cc.hicore.HookItemLoader.Annotations.VerController;
import cc.hicore.HookItemLoader.Annotations.XPExecutor;
import cc.hicore.HookItemLoader.Annotations.XPItem;
import cc.hicore.HookItemLoader.bridge.BaseXPExecutor;
import cc.hicore.HookItemLoader.bridge.MethodContainer;
import cc.hicore.HookItemLoader.bridge.MethodFinderBuilder;
import cc.hicore.HookItemLoader.bridge.QQVersion;
import cc.hicore.HookItemLoader.bridge.UIInfo;
import cc.hicore.LogUtils.LogUtils;
import cc.hicore.ReflectUtils.MClass;
import cc.hicore.ReflectUtils.MField;
import cc.hicore.ReflectUtils.MMethod;
import cc.hicore.ReflectUtils.QQReflect;
import cc.hicore.ReflectUtils.ResUtils;
import cc.hicore.Utils.DataUtils;
import cc.hicore.Utils.Utils;
import cc.hicore.qtool.QQManager.QQEnvUtils;
import cc.hicore.qtool.QQTools.QQDecodeUtils.DecodeForEncPic;
import cc.hicore.qtool.R;
import cc.hicore.qtool.StickerPanelPlus.EmoPanel;
import cc.hicore.qtool.StickerPanelPlus.ICreator;
import cc.hicore.qtool.StickerPanelPlus.PanelUtils;

/*
注入主界面选项菜单,同时在菜单勾选时请求三个钩子的挂钩确认
 */
@SuppressLint("ResourceType")
@XPItem(name = "表情面板#2", itemType = XPItem.ITEM_Hook,targetVer = QQVersion.QQ_8_9_58)
public class StickerPanelJHook {
    @VerController
    @UIItem
    public UIInfo getUI() {
        UIInfo ui = new UIInfo();
        ui.name = "表情面板";
        ui.groupName = "聊天辅助";
        ui.desc = "简洁模式长按表情图标即可";
        ui.type = 1;
        ui.targetID = 1;
        return ui;
    }

    @VerController
    @MethodScanner
    public void getMethod(MethodContainer container) {
        container.addMethod(MethodFinderBuilder.newFinderByString("hook_simple_emo_button_create","funBtnLayout.findViewById(R.id.fun_btn)",m -> true));
    }
    @VerController
    @XPExecutor(methodID = "hook_simple_emo_button_create",period = XPExecutor.After)
    public BaseXPExecutor simple_hook(){
        return param -> {
            int id = QQEnvUtils.getTargetID("emo_btn");
            View v = MField.GetFieldFilter(param.thisObject,ImageButton.class,f-> f != null && f.getId() == id);
            v.setOnLongClickListener(v1 -> {
                ICreator.createPanel(v1.getContext());
                return true;
            });
        };
    }
}
