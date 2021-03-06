package fm.qingting.qtradio.jd.view;

import android.content.Context;
import android.graphics.Canvas;
import android.view.View.MeasureSpec;
import fm.qingting.framework.view.ButtonViewElement;
import fm.qingting.framework.view.ImageViewElement;
import fm.qingting.framework.view.NetImageViewElement;
import fm.qingting.framework.view.QtView;
import fm.qingting.framework.view.TextViewElement;
import fm.qingting.framework.view.TextViewElement.VerticalAlignment;
import fm.qingting.framework.view.ViewElement;
import fm.qingting.framework.view.ViewElement.OnElementClickListener;
import fm.qingting.framework.view.ViewLayout;
import fm.qingting.qtradio.controller.ControllerManager;
import fm.qingting.qtradio.jd.data.CommodityInfo;
import fm.qingting.qtradio.manager.SkinManager;
import fm.qingting.qtradio.view.playview.LineElement;
import fm.qingting.utils.TimeUtil;

public class JDItemView extends QtView
  implements ViewElement.OnElementClickListener
{
  private final ViewLayout avatarLayout = this.itemLayout.createChildLT(120, 120, 25, 24, ViewLayout.SCALE_FLAG_SLTCW);
  private final ViewLayout heatLayout = this.itemLayout.createChildLT(22, 22, 152, 117, ViewLayout.SCALE_FLAG_SLTCW);
  private final ViewLayout hostLabelLayout = this.itemLayout.createChildLT(22, 22, 344, 117, ViewLayout.SCALE_FLAG_SLTCW);
  private final ViewLayout hostTextLayout = this.itemLayout.createChildLT(130, 45, 368, 105, ViewLayout.SCALE_FLAG_SLTCW);
  private final ViewLayout indentationLayout = this.itemLayout.createChildLT(17, 18, 0, 0, ViewLayout.SCALE_FLAG_SLTCW);
  private final ViewLayout infoLayout = this.itemLayout.createChildLT(300, 45, 190, 95, ViewLayout.SCALE_FLAG_SLTCW);
  private final ViewLayout itemLayout = ViewLayout.createViewLayoutWithBoundsLT(720, 168, 720, 168, 0, 0, ViewLayout.SCALE_FLAG_SLTCW);
  private final ViewLayout lineLayout = this.itemLayout.createChildLT(670, 1, 25, 0, ViewLayout.SCALE_FLAG_SLTCW);
  private NetImageViewElement mAvatarElement;
  private ButtonViewElement mBg;
  private ImageViewElement mHeatElement;
  private ImageViewElement mHostLabelElement;
  private TextViewElement mHostTextElement;
  private CommodityInfo mInfo;
  private TextViewElement mInfoElement;
  private LineElement mLineElement;
  private TextViewElement mSubTitleElement;
  private TextViewElement mTimeElement;
  private TextViewElement mTitleElement;
  private final ViewLayout subLayout = this.itemLayout.createChildLT(540, 40, 170, 71, ViewLayout.SCALE_FLAG_SLTCW);
  private final ViewLayout timeLayout = this.itemLayout.createChildLT(280, 40, 170, 111, ViewLayout.SCALE_FLAG_SLTCW);
  private final ViewLayout titleLayout = this.itemLayout.createChildLT(540, 40, 170, 25, ViewLayout.SCALE_FLAG_SLTCW);

  public JDItemView(Context paramContext, int paramInt)
  {
    super(paramContext);
    this.mBg = new ButtonViewElement(paramContext);
    this.mBg.setBackgroundColor(0, SkinManager.getCardColor());
    addElement(this.mBg);
    this.mBg.setOnElementClickListener(this);
    this.mAvatarElement = new NetImageViewElement(paramContext);
    this.mAvatarElement.setDefaultImageRes(2130837902);
    this.mAvatarElement.setBoundColor(SkinManager.getDividerColor());
    addElement(this.mAvatarElement, paramInt);
    this.mTitleElement = new TextViewElement(paramContext);
    this.mTitleElement.setColor(SkinManager.getTextColorNormal());
    this.mTitleElement.setVerticalAlignment(TextViewElement.VerticalAlignment.CENTER);
    this.mTitleElement.setMaxLineLimit(2);
    addElement(this.mTitleElement);
    this.mSubTitleElement = new TextViewElement(paramContext);
    this.mSubTitleElement.setColor(SkinManager.getTextColorRecommend());
    this.mSubTitleElement.setMaxLineLimit(1);
    addElement(this.mSubTitleElement);
    this.mHeatElement = new ImageViewElement(paramContext);
    this.mHeatElement.setImageRes(2130837744);
    this.mInfoElement = new TextViewElement(paramContext);
    this.mInfoElement.setColor(SkinManager.getTextColorHeat());
    this.mInfoElement.setMaxLineLimit(1);
    addElement(this.mInfoElement);
    this.mHostLabelElement = new ImageViewElement(paramContext);
    this.mHostLabelElement.setImageRes(2130837745);
    this.mHostTextElement = new TextViewElement(paramContext);
    this.mHostTextElement.setColor(SkinManager.getTextColorThirdLevel());
    this.mHostTextElement.setMaxLineLimit(1);
    this.mTimeElement = new TextViewElement(paramContext);
    this.mTimeElement.setColor(SkinManager.getTextColorThirdLevel());
    this.mTimeElement.setMaxLineLimit(1);
    addElement(this.mTimeElement);
    this.mLineElement = new LineElement(paramContext);
    this.mLineElement.setOrientation(1);
    this.mLineElement.setColor(SkinManager.getDividerColor());
    addElement(this.mLineElement);
  }

  protected void onDraw(Canvas paramCanvas)
  {
    int i = this.mTitleElement.getLineCnt();
    TextViewElement localTextViewElement = this.mSubTitleElement;
    if (i > 1);
    for (int j = 4; ; j = 0)
    {
      localTextViewElement.setVisible(j);
      super.onDraw(paramCanvas);
      return;
    }
  }

  public void onElementClick(ViewElement paramViewElement)
  {
    ControllerManager.getInstance().openJDShop(this.mInfo);
  }

  protected void onMeasure(int paramInt1, int paramInt2)
  {
    this.itemLayout.scaleToBounds(View.MeasureSpec.getSize(paramInt1), View.MeasureSpec.getSize(paramInt2));
    this.avatarLayout.scaleToBounds(this.itemLayout);
    this.titleLayout.scaleToBounds(this.itemLayout);
    this.subLayout.scaleToBounds(this.itemLayout);
    this.infoLayout.scaleToBounds(this.itemLayout);
    this.lineLayout.scaleToBounds(this.itemLayout);
    this.timeLayout.scaleToBounds(this.itemLayout);
    this.heatLayout.scaleToBounds(this.itemLayout);
    this.hostLabelLayout.scaleToBounds(this.itemLayout);
    this.hostTextLayout.scaleToBounds(this.itemLayout);
    this.indentationLayout.scaleToBounds(this.itemLayout);
    this.mBg.measure(this.itemLayout);
    this.mAvatarElement.measure(this.avatarLayout);
    this.mAvatarElement.setBoundLineWidth(this.lineLayout.height);
    this.mTitleElement.measure(this.titleLayout);
    this.mSubTitleElement.measure(this.subLayout);
    this.mHeatElement.measure(this.heatLayout);
    this.mInfoElement.measure(this.infoLayout);
    this.mTimeElement.measure(this.timeLayout);
    this.mLineElement.measure(this.lineLayout.leftMargin, this.itemLayout.height - this.lineLayout.height, this.lineLayout.getRight(), this.itemLayout.height);
    this.mTitleElement.setTextSize(SkinManager.getInstance().getNormalTextSize());
    this.mSubTitleElement.setTextSize(SkinManager.getInstance().getSubTextSize());
    this.mInfoElement.setTextSize(SkinManager.getInstance().getRecommendTextSize());
    this.mTimeElement.setTextSize(SkinManager.getInstance().getTeenyTinyTextSize());
    this.mHostLabelElement.measure(this.hostLabelLayout);
    this.mHostTextElement.measure(this.hostTextLayout);
    this.mHostTextElement.setTextSize(SkinManager.getInstance().getRecommendTextSize());
    setMeasuredDimension(this.itemLayout.width, this.itemLayout.height);
  }

  public void update(String paramString, Object paramObject)
  {
    if (paramString.equalsIgnoreCase("content"))
    {
      this.mInfo = ((CommodityInfo)paramObject);
      this.mAvatarElement.setImageUrl(this.mInfo.getAvatar());
      this.mTitleElement.setText(this.mInfo.getTitle(), false);
      this.mSubTitleElement.setText(this.mInfo.getPrice(), false);
      this.mTimeElement.setText(TimeUtil.getReadableTime(this.mInfo.getUpdateTime()));
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.jd.view.JDItemView
 * JD-Core Version:    0.6.2
 */