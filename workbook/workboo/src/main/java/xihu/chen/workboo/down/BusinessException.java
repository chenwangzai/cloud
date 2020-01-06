package xihu.chen.workboo.down;

public class BusinessException extends RuntimeException {

    public BusinessException(){
        super();
    }
    public BusinessException(String message){
        super(message);
    }
    public BusinessException(String messae,Throwable throwable){
        super(messae,throwable);
    }
    public BusinessException(Throwable throwable){
        super(throwable);
    }
}
