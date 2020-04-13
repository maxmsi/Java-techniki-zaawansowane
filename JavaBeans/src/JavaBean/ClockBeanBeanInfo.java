package JavaBean;

import java.awt.*;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.beans.SimpleBeanInfo;

public class ClockBeanBeanInfo extends SimpleBeanInfo {

    private static Image icon = null;
    private static String iconName = "clock.png";

    public PropertyDescriptor[] getPropertyDescriptors() {
        try {

            return new PropertyDescriptor[] {
                    new PropertyDescriptor("title", JavaBean.ClockBean.class, "getTitle", "setTitle"),
                    new PropertyDescriptor("myBackground", JavaBean.ClockBean.class, "getMyBackground", "setMyBackground"),
                    new PropertyDescriptor("myFonts", JavaBean.ClockBean.class, "getMyFonts", "setMyFonts")
            };
        } catch (IntrospectionException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Image getIcon(int iconKind) {
        if (iconName == null) {
            return null;
        } else {
            if (icon == null) {
                icon = loadImage(iconName);
            }
            return icon;
        }
    }
}
