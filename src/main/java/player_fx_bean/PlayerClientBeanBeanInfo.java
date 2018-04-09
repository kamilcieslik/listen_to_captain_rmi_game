package player_fx_bean;

import javax.imageio.ImageIO;
import java.awt.*;
import java.beans.BeanDescriptor;
import java.beans.BeanInfo;
import java.beans.SimpleBeanInfo;
import java.io.IOException;

public class PlayerClientBeanBeanInfo extends SimpleBeanInfo {
    private BeanDescriptor beanDescriptor = new BeanDescriptor(PlayerClientBean.class);

    @Override
    public Image getIcon(int iconKind) {
        String imageFilename;
        if (iconKind == BeanInfo.ICON_COLOR_16x16)
            imageFilename = "bean_info_icon_color_16_16.gif";
        else if (iconKind == BeanInfo.ICON_COLOR_32x32)
            imageFilename = "bean_info_icon_color_32_32.gif";
        else if (iconKind == BeanInfo.ICON_MONO_16x16)
            imageFilename = "bean_info_icon_mono_16_16.gif";
        else if (iconKind == BeanInfo.ICON_MONO_32x32)
            imageFilename = "bean_info_icon_mono_32_32.gif";
        else
            return null;

        Image iconImage = null;
        try {
            iconImage = ImageIO.read(getClass().getResource("/image/" + imageFilename));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return iconImage;
    }

    public BeanDescriptor getBeanDescriptor() {
        return beanDescriptor;
    }
}
