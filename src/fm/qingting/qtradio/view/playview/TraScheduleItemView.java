package fm.qingting.qtradio.view.playview;

import android.content.Context;
import android.graphics.Canvas;
import android.view.View.MeasureSpec;
import android.widget.Toast;
import fm.qingting.framework.view.ButtonViewElement;
import fm.qingting.framework.view.ImageViewElement;
import fm.qingting.framework.view.QtView;
import fm.qingting.framework.view.TextViewElement;
import fm.qingting.framework.view.ViewElement;
import fm.qingting.framework.view.ViewElement.OnElementClickListener;
import fm.qingting.framework.view.ViewLayout;
import fm.qingting.qtradio.controller.ControllerManager;
import fm.qingting.qtradio.fm.PlayerAgent;
import fm.qingting.qtradio.fmdriver.FMManager;
import fm.qingting.qtradio.manager.SkinManager;
import fm.qingting.qtradio.model.InfoManager;
import fm.qingting.qtradio.model.Node;
import fm.qingting.qtradio.model.PersonalCenterNode;
import fm.qingting.qtradio.model.ProgramNode;
import fm.qingting.qtradio.model.RadioChannelNode;
import fm.qingting.qtradio.model.ReserveInfoNode;
import fm.qingting.qtradio.model.RootNode;
import fm.qingting.qtradio.model.RootNode.PlayMode;
import java.util.Locale;

public class TraScheduleItemView extends QtView
{
  private static final String MODEL_NORMAL = "%s-%s";
  private static final String MODEL_REPLAY = "回听：%s-%s";
  private static final String RESERVE = "预约";
  private static final String RESERVED = "已预约";
  private final ViewLayout arrowLayout = this.itemLayout.createChildLT(36, 36, 650, 50, ViewLayout.SCALE_FLAG_SLTCW);
  private final ViewLayout infoLayout = this.itemLayout.createChildLT(600, 45, 30, 75, ViewLayout.SCALE_FLAG_SLTCW);
  private final ViewLayout itemLayout = ViewLayout.createViewLayoutWithBoundsLT(720, 136, 720, 800, 0, 0, ViewLayout.SCALE_FLAG_SLTCW);
  private final ViewLayout lineLayout = this.itemLayout.createChildLT(690, 1, 30, 135, ViewLayout.SCALE_FLAG_SLTCW);
  private final ViewLayout livingLayout = this.itemLayout.createChildLT(50, 26, 30, 29, ViewLayout.SCALE_FLAG_SLTCW);
  private ImageViewElement mArrowElement;
  private ButtonViewElement mBgElement;
  private TextViewElement mInfoElement;
  private boolean mIsPlaying = false;
  private LineElement mLineElement;
  private TextViewElement mNameElement;
  private ProgramNode mNode;
  private ImageViewElement mPlayStateElement;
  private TextViewElement mPlayStateTextElement;
  private int mState;
  private ImageViewElement mStateElement;
  private final ViewLayout nameLayout = this.itemLayout.createChildLT(600, 45, 30, 20, ViewLayout.SCALE_FLAG_SLTCW);
  private final ViewLayout paddingLayout = this.itemLayout.createChildLT(10, 10, 0, 0, ViewLayout.SCALE_FLAG_SLTCW);
  private final ViewLayout playStateLayout = this.itemLayout.createChildLT(68, 30, 30, 82, ViewLayout.SCALE_FLAG_SLTCW);
  private final ViewLayout playStateTextLayout = this.itemLayout.createChildLT(80, 45, 30, 75, ViewLayout.SCALE_FLAG_SLTCW);

  public TraScheduleItemView(Context paramContext, int paramInt)
  {
    super(paramContext);
    this.mBgElement = new ButtonViewElement(paramContext);
    this.mBgElement.setBackgroundColor(SkinManager.getItemHighlightMaskColor(), 0);
    addElement(this.mBgElement);
    this.mBgElement.setOnElementClickListener(new ViewElement.OnElementClickListener()
    {
      public void onElementClick(ViewElement paramAnonymousViewElement)
      {
        TraScheduleItemView.this.handleClick();
      }
    });
    this.mNameElement = new TextViewElement(paramContext);
    this.mNameElement.setMaxLineLimit(1);
    this.mNameElement.setColor(-1);
    addElement(this.mNameElement);
    this.mInfoElement = new TextViewElement(paramContext);
    this.mInfoElement.setMaxLineLimit(1);
    this.mInfoElement.setColor(SkinManager.getNewPlaySubColor());
    addElement(this.mInfoElement);
    this.mStateElement = new ImageViewElement(paramContext);
    this.mStateElement.setImageRes(2130837921);
    addElement(this.mStateElement, paramInt);
    this.mLineElement = new LineElement(paramContext);
    this.mLineElement.setColor(872415231);
    this.mLineElement.setOrientation(1);
    addElement(this.mLineElement);
    this.mArrowElement = new ImageViewElement(paramContext);
    this.mArrowElement.setImageRes(2130837694);
    addElement(this.mArrowElement, paramInt);
    this.mPlayStateElement = new ImageViewElement(paramContext);
    addElement(this.mPlayStateElement, paramInt);
    this.mPlayStateTextElement = new TextViewElement(paramContext);
    this.mPlayStateTextElement.setMaxLineLimit(1);
    this.mPlayStateTextElement.setColor(-1);
    addElement(this.mPlayStateTextElement);
  }

  private void handleClick()
  {
    if (this.mNode == null)
      return;
    if (this.mNode.getCurrPlayStatus() == 2)
    {
      if (InfoManager.getInstance().root().mPersonalCenterNode.reserveNode.isExisted(this.mNode))
      {
        InfoManager.getInstance().root().mPersonalCenterNode.reserveNode.cancelReserve(this.mNode.id);
        this.mPlayStateTextElement.setText("预约");
      }
      while (true)
      {
        invalidate();
        return;
        InfoManager.getInstance().root().mPersonalCenterNode.reserveNode.addReserveNode(this.mNode);
        this.mPlayStateTextElement.setText("已预约");
      }
    }
    if (InfoManager.getInstance().root().currentPlayMode() == RootNode.PlayMode.FMPLAY)
    {
      Toast.makeText(getContext(), "亲，无法使用系统收音机回听节目，只能播放当前直播节目", 1).show();
      boolean bool1 = this.mNode.nodeName.equalsIgnoreCase("program");
      int i = 0;
      if (bool1)
      {
        Node localNode = this.mNode.parent;
        i = 0;
        if (localNode != null)
        {
          boolean bool2 = this.mNode.parent.nodeName.equalsIgnoreCase("radiochannel");
          i = 0;
          if (bool2)
            i = Integer.valueOf(((RadioChannelNode)this.mNode.parent).freq).intValue();
        }
      }
      if (i != 0)
        FMManager.getInstance().tune(i);
      PlayerAgent.getInstance().dispatchPlayStateInFM(4096);
      InfoManager.getInstance().root().tuneFM(true);
    }
    while (true)
    {
      invalidate();
      return;
      PlayerAgent.getInstance().play(this.mNode);
      ControllerManager.getInstance().popLastController();
    }
  }

  private boolean isPlaying(ProgramNode paramProgramNode)
  {
    if (paramProgramNode == null)
      return false;
    Node localNode = InfoManager.getInstance().root().getCurrentPlayingNode();
    if (localNode == null)
      return false;
    if (!this.mNode.nodeName.equalsIgnoreCase(localNode.nodeName))
      return false;
    return this.mNode.id == ((ProgramNode)localNode).id;
  }

  protected void onDraw(Canvas paramCanvas)
  {
    TextViewElement localTextViewElement = this.mNameElement;
    int i;
    if (this.mIsPlaying)
    {
      i = this.livingLayout.width;
      localTextViewElement.setTranslationX(i);
      switch (this.mState)
      {
      default:
      case 1:
      case 2:
      case 3:
      }
    }
    while (true)
    {
      super.onDraw(paramCanvas);
      return;
      i = 0;
      break;
      this.mInfoElement.setTranslationX(this.playStateLayout.width + this.paddingLayout.width);
      continue;
      int j = this.mPlayStateTextElement.getWidth();
      if (j > this.playStateLayout.width)
      {
        this.mInfoElement.setTranslationX(j + this.paddingLayout.width);
      }
      else
      {
        this.mInfoElement.setTranslationX(this.playStateLayout.width + this.paddingLayout.width);
        continue;
        this.mInfoElement.setTranslationX(0);
      }
    }
  }

  protected void onMeasure(int paramInt1, int paramInt2)
  {
    this.itemLayout.scaleToBounds(View.MeasureSpec.getSize(paramInt1), View.MeasureSpec.getSize(paramInt2));
    this.nameLayout.scaleToBounds(this.itemLayout);
    this.infoLayout.scaleToBounds(this.itemLayout);
    this.arrowLayout.scaleToBounds(this.itemLayout);
    this.lineLayout.scaleToBounds(this.itemLayout);
    this.livingLayout.scaleToBounds(this.itemLayout);
    this.playStateTextLayout.scaleToBounds(this.itemLayout);
    this.playStateLayout.scaleToBounds(this.itemLayout);
    this.paddingLayout.scaleToBounds(this.itemLayout);
    this.mNameElement.measure(this.nameLayout);
    this.mInfoElement.measure(this.infoLayout);
    this.mArrowElement.measure(this.arrowLayout);
    this.mBgElement.measure(this.itemLayout);
    this.mStateElement.measure(this.livingLayout);
    this.mLineElement.measure(this.lineLayout);
    this.mPlayStateElement.measure(this.playStateLayout);
    this.mPlayStateTextElement.measure(this.playStateTextLayout);
    this.mNameElement.setTextSize(SkinManager.getInstance().getNormalTextSize());
    this.mInfoElement.setTextSize(SkinManager.getInstance().getSubTextSize());
    this.mPlayStateTextElement.setTextSize(SkinManager.getInstance().getSubTextSize());
    setMeasuredDimension(this.itemLayout.width, this.itemLayout.height);
  }

  public void update(String paramString, Object paramObject)
  {
    ImageViewElement localImageViewElement;
    if (paramString.equalsIgnoreCase("content"))
    {
      this.mNode = ((ProgramNode)paramObject);
      this.mIsPlaying = isPlaying(this.mNode);
      localImageViewElement = this.mStateElement;
      if (!this.mIsPlaying)
        break label168;
    }
    label168: for (int i = 0; ; i = 4)
    {
      localImageViewElement.setVisible(i);
      this.mNameElement.setText(this.mNode.title, false);
      this.mState = this.mNode.getCurrPlayStatus();
      if (this.mState != 1)
        break;
      Locale localLocale3 = Locale.CHINA;
      Object[] arrayOfObject3 = new Object[2];
      arrayOfObject3[0] = this.mNode.startTime;
      arrayOfObject3[1] = this.mNode.endTime;
      String str3 = String.format(localLocale3, "%s-%s", arrayOfObject3);
      this.mPlayStateElement.setImageRes(2130837796);
      this.mPlayStateElement.setVisible(0);
      this.mPlayStateTextElement.setVisible(4);
      this.mInfoElement.setText(str3);
      return;
    }
    if (this.mState == 2)
    {
      if (InfoManager.getInstance().root().mPersonalCenterNode.reserveNode.isExisted(this.mNode))
        this.mPlayStateTextElement.setText("已预约");
      while (true)
      {
        this.mPlayStateElement.setVisible(4);
        this.mPlayStateTextElement.setVisible(0);
        Locale localLocale2 = Locale.CHINA;
        Object[] arrayOfObject2 = new Object[2];
        arrayOfObject2[0] = this.mNode.startTime;
        arrayOfObject2[1] = this.mNode.endTime;
        String str2 = String.format(localLocale2, "%s-%s", arrayOfObject2);
        this.mInfoElement.setText(str2);
        return;
        this.mPlayStateTextElement.setText("预约");
      }
    }
    Locale localLocale1 = Locale.CHINA;
    Object[] arrayOfObject1 = new Object[2];
    arrayOfObject1[0] = this.mNode.startTime;
    arrayOfObject1[1] = this.mNode.endTime;
    String str1 = String.format(localLocale1, "回听：%s-%s", arrayOfObject1);
    this.mPlayStateElement.setVisible(4);
    this.mPlayStateTextElement.setVisible(4);
    this.mInfoElement.setText(str1);
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.view.playview.TraScheduleItemView
 * JD-Core Version:    0.6.2
 */