package cn.wsgwz.basemodule.data;


public class ProgressInfo {
    private long bytesRead;
    private long contentLength;


    public ProgressInfo(long bytesRead, long contentLength) {
        this.bytesRead = bytesRead;
        this.contentLength = contentLength;
    }



    public long getContentLength() {
        return contentLength;
    }

    public void setContentLength(long contentLength) {
        this.contentLength = contentLength;
    }

    public long getBytesRead() {
        return bytesRead;
    }

    public void setBytesRead(long bytesRead) {
        this.bytesRead = bytesRead;
    }

}
