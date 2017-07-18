package android.com.myqq.been;


public class ChatMsgBean {
    private static final String TAG ="ChatMsgBean";

    private String name;

    private String date;

    private String text;

    private boolean isComMeg = true;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean getMsgType() {
        return isComMeg;
    }

    public void setMsgType(boolean isComMsg) {
    	isComMeg = isComMsg;
    }

    public ChatMsgBean() {
    }

    public ChatMsgBean(String name, String date, String text, boolean isComMsg) {
        super();
        this.name = name;
        this.date = date;
        this.text = text;
        this.isComMeg = isComMsg;
    }

    @Override
    public String toString() {
        return "ChatMsgBean{" +
                "date='" + date + '\'' +
                ", name='" + name + '\'' +
                ", text='" + text + '\'' +
                ", isComMeg=" + isComMeg +
                '}';
    }
}
