package studio.contrarian.xphunt.app.service;

public interface PermissionService{

    public void verifyHunterIsMemberOfRoom(Long roomId, Long hunterId) ;

    public void verifyHunterCanAccessTask(Long taskId, Long hunterId);

    public void verifyHunterIsTaskCreator(Long taskId, Long hunterId);
}
