package fm.qingting.qtradio.view.moreContentView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.graphics.RectF;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.TextUtils.TruncateAt;
import android.view.View;
import android.view.View.MeasureSpec;
import android.widget.Toast;
import fm.qingting.framework.utils.BitmapResourceCache;
import fm.qingting.framework.view.QtListItemView;
import fm.qingting.framework.view.ViewLayout;
import fm.qingting.qtradio.alarm.ClockManager;
import fm.qingting.qtradio.controller.ControllerManager;
import fm.qingting.qtradio.helper.PodcasterHelper;
import fm.qingting.qtradio.im.IMAgent;
import fm.qingting.qtradio.im.LatestMessages;
import fm.qingting.qtradio.im.message.IMMessage;
import fm.qingting.qtradio.manager.SkinManager;
import fm.qingting.qtradio.model.AlarmInfoNode;
import fm.qingting.qtradio.model.ChannelNode;
import fm.qingting.qtradio.model.CollectionNode;
import fm.qingting.qtradio.model.InfoManager;
import fm.qingting.qtradio.model.Node;
import fm.qingting.qtradio.model.PersonalCenterNode;
import fm.qingting.qtradio.model.PlayHistoryInfoNode;
import fm.qingting.qtradio.model.ReserveInfoNode;
import fm.qingting.qtradio.model.RootNode;
import fm.qingting.qtradio.room.SnsInfo;
import fm.qingting.qtradio.room.UserInfo;
import fm.qingting.qtradio.social.CloudCenter;
import fm.qingting.qtradio.social.UserProfile;
import fm.qingting.qtradio.wo.WoApiRequest;
import fm.qingting.utils.QTMSGManage;
import java.util.List;
import java.util.Locale;

public class UserinfoItemView extends QtListItemView
{
  private static final String COLLECTION_MODEL = "%d个电台，%d个有更新";
  private static final String COLLECTION_MODEL_SIMPLE = "%d个电台";
  private static final String CONTACTS_MODEL = "共有%d个联系人";
  private static final String EMPTY_ALARM = "闹钟未开启";
  private static final String EMPTY_COLLECTION = "收藏喜欢的内容，更新及时告诉您";
  private static final String EMPTY_CONTACTS = "暂无联系人";
  private static final String EMPTY_MESSAGE = "无消息";
  private static final String EMPTY_MORE = "缓存，音质，推送，使用帮助等";
  private static final String EMPTY_PODCASTER = "关注喜爱的主播，更新及时告诉您";
  private static final String EMPTY_TIMER = "未设置";
  private static final String ME = "我:";
  private static final String PODCASER_MODEL_SIMPLE = "%d个关注";
  private final String MODEL_TIMER = "距离关闭还有%02d:%02d";
  private final ViewLayout flowRemindLayout = this.itemLayout.createChildLT(18, 18, 330, 34, ViewLayout.SCALE_FLAG_SLTCW);
  private final ViewLayout iconLayout = this.itemLayout.createChildLT(68, 68, 30, 34, ViewLayout.SCALE_FLAG_SLTCW);
  private final ViewLayout infoLayout = this.itemLayout.createChildLT(720, 45, 128, 75, ViewLayout.SCALE_FLAG_SLTCW);
  private final ViewLayout itemLayout = ViewLayout.createViewLayoutWithBoundsLT(720, 136, 720, 136, 0, 0, ViewLayout.SCALE_FLAG_SLTCW);
  private final ViewLayout lineLayout = this.itemLayout.createChildLT(720, 1, 128, 0, ViewLayout.SCALE_FLAG_SLTCW);
  private boolean mHasOpen;
  private final Rect mIconRect = new Rect();
  private boolean mNeedBottomLine = false;
  private final Paint mPaint = new Paint();
  private final Paint mReminderPaint = new Paint();
  private final Rect mTextBound = new Rect();
  private final Paint mTipBgPaint = new Paint();
  private final Paint mTipPaint = new Paint();
  private final RectF mTipRectF = new RectF();
  private int mType = 0;
  private int mUpdateCnt = 0;
  private final ViewLayout remindLayout = this.itemLayout.createChildLT(18, 18, 77, 46, ViewLayout.SCALE_FLAG_SLTCW);
  private final ViewLayout tipLayout = this.itemLayout.createChildLT(36, 36, 64, 32, ViewLayout.SCALE_FLAG_SLTCW);
  private final ViewLayout titleLayout = this.itemLayout.createChildLT(720, 45, 128, 20, ViewLayout.SCALE_FLAG_SLTCW);

  public UserinfoItemView(Context paramContext)
  {
    super(paramContext);
    this.mReminderPaint.setColor(-59877);
    this.mReminderPaint.setStyle(Paint.Style.FILL);
    this.mTipBgPaint.setColor(SkinManager.getDownloadTipBgColor());
    this.mTipPaint.setColor(SkinManager.getBackgroundColor());
    setItemSelectedEnable();
    setOnClickListener(this);
    this.mHasOpen = false;
  }

  private void drawBg(Canvas paramCanvas)
  {
    if (isItemPressed())
      return;
    paramCanvas.drawColor(SkinManager.getCardColor());
  }

  private void drawFlowReminder(Canvas paramCanvas)
  {
    paramCanvas.drawCircle(this.flowRemindLayout.leftMargin + this.flowRemindLayout.width / 2, this.flowRemindLayout.topMargin + this.flowRemindLayout.height / 2, this.flowRemindLayout.width / 2, this.mReminderPaint);
  }

  private void drawIcon(Canvas paramCanvas)
  {
    paramCanvas.drawBitmap(BitmapResourceCache.getInstance().getResourceCache(getResources(), this, UserInfoType.getRes(this.mType)), null, this.mIconRect, this.mPaint);
  }

  private void drawInfo(Canvas paramCanvas)
  {
    if (this.mType == 8)
    {
      this.mHasOpen = WoApiRequest.hasOpen();
      if (!this.mHasOpen)
        break label134;
    }
    label134: for (TextPaint localTextPaint = SkinManager.getInstance().getHighlightTextPaint(); ; localTextPaint = SkinManager.getInstance().getSubTextPaint())
    {
      String str = TextUtils.ellipsize(getInfo(), localTextPaint, this.itemLayout.width - this.infoLayout.leftMargin, TextUtils.TruncateAt.END).toString();
      localTextPaint.getTextBounds(str, 0, str.length(), this.mTextBound);
      paramCanvas.drawText(str, this.infoLayout.leftMargin, this.infoLayout.topMargin + (this.infoLayout.height - this.mTextBound.top - this.mTextBound.bottom) / 2, localTextPaint);
      return;
      this.mHasOpen = false;
      break;
    }
  }

  private void drawLine(Canvas paramCanvas)
  {
    if (this.mNeedBottomLine)
      SkinManager.getInstance().drawHorizontalLine(paramCanvas, this.lineLayout.leftMargin, this.itemLayout.width, this.itemLayout.height - this.lineLayout.height, this.lineLayout.height);
  }

  private void drawReminder(Canvas paramCanvas)
  {
    paramCanvas.drawCircle(this.remindLayout.leftMargin + this.remindLayout.width / 2, this.remindLayout.topMargin + this.remindLayout.height / 2, this.remindLayout.width / 2, this.mReminderPaint);
  }

  private void drawTip(Canvas paramCanvas)
  {
    int i = getTipCount();
    if (i <= 0)
      return;
    if (i >= 100);
    int j;
    int k;
    for (String str = "99+"; ; str = String.valueOf(i))
    {
      this.mTipPaint.getTextBounds(str, 0, str.length(), this.mTextBound);
      j = this.mTextBound.right + this.mTextBound.left;
      k = getUpperLimit();
      if (j > k)
        break;
      float f1 = this.tipLayout.leftMargin + this.tipLayout.width / 2;
      float f2 = this.tipLayout.topMargin + this.tipLayout.height / 2;
      paramCanvas.drawCircle(f1, f2, this.tipLayout.width / 2, this.mTipBgPaint);
      paramCanvas.drawText(str, f1 - (this.mTextBound.right + this.mTextBound.right) / 2, f2 - (this.mTextBound.top + this.mTextBound.bottom) / 2, this.mTipPaint);
      return;
    }
    this.mTipRectF.set(this.tipLayout.leftMargin, this.tipLayout.topMargin, j + (this.tipLayout.leftMargin + this.tipLayout.width) - k, this.tipLayout.getBottom());
    paramCanvas.drawRoundRect(this.mTipRectF, this.tipLayout.width / 2, this.tipLayout.height / 2, this.mTipBgPaint);
    paramCanvas.drawText(str, this.mTipRectF.centerX() - (this.mTextBound.right + this.mTextBound.left) / 2, this.mTipRectF.centerY() - (this.mTextBound.top + this.mTextBound.bottom) / 2, this.mTipPaint);
  }

  private void drawTitle(Canvas paramCanvas)
  {
    String str = UserInfoType.getTitle(this.mType);
    TextPaint localTextPaint = SkinManager.getInstance().getNormalTextPaint();
    localTextPaint.getTextBounds(str, 0, str.length(), this.mTextBound);
    paramCanvas.drawText(str, this.titleLayout.leftMargin, this.titleLayout.topMargin + (this.titleLayout.height - this.mTextBound.top - this.mTextBound.bottom) / 2, localTextPaint);
    if ((this.mType == 0) && (this.mUpdateCnt > 0))
      drawReminder(paramCanvas);
    this.mHasOpen = WoApiRequest.hasOpen();
    if ((this.mType == 8) && (!this.mHasOpen))
      drawFlowReminder(paramCanvas);
  }

  private void generateRect()
  {
    this.mIconRect.set(this.iconLayout.leftMargin, this.iconLayout.topMargin, this.iconLayout.getRight(), this.iconLayout.getBottom());
  }

  private int getContactsCnt()
  {
    return InfoManager.getInstance().getUserProfile().getContactsCnt();
  }

  private String getInfo()
  {
    String str = "";
    switch (this.mType)
    {
    default:
    case 0:
    case 1:
    case 2:
    case 4:
    case 3:
    case 5:
    case 6:
      do
      {
        return str;
        List localList = InfoManager.getInstance().root().mPersonalCenterNode.myCollectionNode.getFavouriteNodes();
        if ((localList == null) || (localList.size() == 0))
          return "收藏喜欢的内容，更新及时告诉您";
        if (this.mUpdateCnt > 0)
        {
          Locale localLocale3 = Locale.CHINA;
          Object[] arrayOfObject3 = new Object[2];
          arrayOfObject3[0] = Integer.valueOf(localList.size());
          arrayOfObject3[1] = Integer.valueOf(this.mUpdateCnt);
          return String.format(localLocale3, "%d个电台，%d个有更新", arrayOfObject3);
        }
        Locale localLocale2 = Locale.CHINA;
        Object[] arrayOfObject2 = new Object[1];
        arrayOfObject2[0] = Integer.valueOf(localList.size());
        return String.format(localLocale2, "%d个电台", arrayOfObject2);
        return InfoManager.getInstance().root().mPersonalCenterNode.playHistoryNode.getLatestHistoryInfo();
        return InfoManager.getInstance().root().mPersonalCenterNode.reserveNode.getLastestReserveInfo();
        IMMessage localIMMessage = LatestMessages.pickLatestMessage();
        if (localIMMessage != null)
        {
          if ((localIMMessage.mFromName != null) && (localIMMessage.mFromName.length() > 0))
            return localIMMessage.mFromName + ":" + localIMMessage.mMessage;
          return "我:" + localIMMessage.mMessage;
        }
        return "无消息";
        int i = getContactsCnt();
        if (i == 0)
          return "暂无联系人";
        Locale localLocale1 = Locale.CHINESE;
        Object[] arrayOfObject1 = new Object[1];
        arrayOfObject1[0] = Integer.valueOf(i);
        return String.format(localLocale1, "共有%d个联系人", arrayOfObject1);
        if (ClockManager.getInstance().getTimerAvailable())
          return getTimerInfo();
        return "未设置";
        str = InfoManager.getInstance().root().mPersonalCenterNode.alarmInfoNode.getNearestAlarmInfo();
      }
      while ((str != null) && (str.length() != 0));
      return "闹钟未开启";
    case 7:
      return "缓存，音质，推送，使用帮助等";
    case 8:
      this.mHasOpen = WoApiRequest.hasOpen();
      if (this.mHasOpen)
        return "已开通";
      return "海量音频无限畅听，流量全免";
    case 9:
      this.mHasOpen = WoApiRequest.hasOpen();
      return "精品内容，免流量畅听";
    case 10:
      return getMyPodcasterInfo();
    case 11:
    }
    return "生活就是边听边玩";
  }

  private String getMyPodcasterInfo()
  {
    String str = "关注喜爱的主播，更新及时告诉您";
    if (CloudCenter.getInstance().isLogin())
    {
      UserProfile localUserProfile = InfoManager.getInstance().getUserProfile();
      if ((localUserProfile.getUserInfo() != null) && (!TextUtils.isEmpty(localUserProfile.getUserInfo().snsInfo.sns_id)))
      {
        List localList = PodcasterHelper.getInstance().getAllMyPodcaster(localUserProfile.getUserInfo().snsInfo.sns_id);
        if (localList.size() > 0)
        {
          Object[] arrayOfObject = new Object[1];
          arrayOfObject[0] = Integer.valueOf(localList.size());
          str = String.format("%d个关注", arrayOfObject);
        }
      }
    }
    return str;
  }

  private String getTimerInfo()
  {
    ClockManager localClockManager = ClockManager.getInstance();
    if (localClockManager.getTimerAvailable())
    {
      int i = localClockManager.getTimerLeft();
      if (i < 0)
        i = 0;
      Locale localLocale = Locale.US;
      Object[] arrayOfObject = new Object[2];
      arrayOfObject[0] = Integer.valueOf(i / 60);
      arrayOfObject[1] = Integer.valueOf(i % 60);
      return String.format(localLocale, "距离关闭还有%02d:%02d", arrayOfObject);
    }
    return "";
  }

  private int getTipCount()
  {
    switch (this.mType)
    {
    default:
    case 4:
    }
    do
      return 0;
    while (!CloudCenter.getInstance().isLogin());
    return IMAgent.getInstance().getUnreadCnt();
  }

  private int getUpdateCnt()
  {
    List localList = InfoManager.getInstance().root().mPersonalCenterNode.myCollectionNode.getFavouriteNodes();
    if ((localList == null) || (localList.size() == 0));
    while (true)
    {
      return 0;
      int i = localList.size();
      for (int j = 0; j < i; j++)
      {
        Node localNode = (Node)localList.get(j);
        if ((!localNode.nodeName.equalsIgnoreCase("channel")) || (((ChannelNode)localNode).channelType != 1));
      }
    }
  }

  private int getUpperLimit()
  {
    return 2 * this.tipLayout.width / 3;
  }

  public Object getValue(String paramString, Object paramObject)
  {
    if (paramString.equalsIgnoreCase("type"))
      return Integer.valueOf(this.mType);
    return super.getValue(paramString, paramObject);
  }

  protected void onDraw(Canvas paramCanvas)
  {
    paramCanvas.setDrawFilter(SkinManager.getInstance().getDrawFilter());
    paramCanvas.save();
    if (this.mType == 0)
      this.mUpdateCnt = getUpdateCnt();
    drawBg(paramCanvas);
    drawIcon(paramCanvas);
    drawTitle(paramCanvas);
    drawInfo(paramCanvas);
    drawLine(paramCanvas);
    drawTip(paramCanvas);
    paramCanvas.restore();
  }

  protected void onMeasure(int paramInt1, int paramInt2)
  {
    this.itemLayout.scaleToBounds(View.MeasureSpec.getSize(paramInt1), View.MeasureSpec.getSize(paramInt2));
    this.iconLayout.scaleToBounds(this.itemLayout);
    this.titleLayout.scaleToBounds(this.itemLayout);
    this.lineLayout.scaleToBounds(this.itemLayout);
    this.remindLayout.scaleToBounds(this.itemLayout);
    this.tipLayout.scaleToBounds(this.itemLayout);
    this.infoLayout.scaleToBounds(this.itemLayout);
    this.flowRemindLayout.scaleToBounds(this.itemLayout);
    this.mTipPaint.setTextSize(SkinManager.getInstance().getTinyTextSize());
    generateRect();
    setMeasuredDimension(this.itemLayout.width, this.itemLayout.height);
  }

  protected void onQtItemClick(View paramView)
  {
    switch (this.mType)
    {
    default:
    case 0:
    case 1:
    case 2:
    case 3:
    case 4:
    case 5:
    case 6:
    case 7:
    case 8:
    case 9:
    case 10:
    case 11:
    }
    while (true)
    {
      QTMSGManage.getInstance().sendStatistcsMessage("personalcenterclick", UserInfoType.getTitle(this.mType));
      if (this.mType == 8)
        WoApiRequest.sendEventMessage("unicomClickFlow");
      if (this.mType == 9)
        WoApiRequest.sendEventMessage("unicomRead");
      return;
      ControllerManager.getInstance().redirectToMyCollectionView();
      continue;
      ControllerManager.getInstance().openPlayHistoryController();
      continue;
      ControllerManager.getInstance().openReserveController();
      continue;
      ControllerManager.getInstance().openImContactsController();
      continue;
      ControllerManager.getInstance().openImConversationsController();
      continue;
      ControllerManager.getInstance().openTimerSettingController();
      continue;
      ControllerManager.getInstance().openAlarmControllerListOrAdd();
      continue;
      ControllerManager.getInstance().openSettingController();
      continue;
      if (WoApiRequest.hasInited())
      {
        ControllerManager.getInstance().openWoController();
      }
      else
      {
        Toast.makeText(getContext(), "网络连接有问题或者正在初始化..", 1).show();
        continue;
        ControllerManager.getInstance().openChinaUnicomZone();
        continue;
        ControllerManager.getInstance().openMyPodcasterController();
        continue;
        ControllerManager.getInstance().openPlayGameController();
      }
    }
  }

  public void update(String paramString, Object paramObject)
  {
    if (paramString.equalsIgnoreCase("content"))
    {
      this.mType = ((Integer)paramObject).intValue();
      invalidate();
    }
    while (!paramString.equalsIgnoreCase("needBottomLine"))
      return;
    this.mNeedBottomLine = ((Boolean)paramObject).booleanValue();
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.view.moreContentView.UserinfoItemView
 * JD-Core Version:    0.6.2
 */