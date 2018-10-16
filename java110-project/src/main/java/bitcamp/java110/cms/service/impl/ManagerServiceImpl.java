package bitcamp.java110.cms.service.impl;

import java.util.HashMap;
import java.util.List;

import bitcamp.java110.cms.dao.ManagerDao;
import bitcamp.java110.cms.dao.MemberDao;
import bitcamp.java110.cms.dao.PhotoDao;
import bitcamp.java110.cms.domain.Manager;
import bitcamp.java110.cms.service.ManagerService;

/*  객체 지향 어느 객체가 어느 객체와 일을 하는지 흐름을 알아야 한다.

    DAO가 DAO사용하는것은 바람직 하지 않다.
    ManagerService 회사마다 다르기때문에 만든다.
    하나의 table은 하나의 DAO에서만 insert 가능하게 하는게 좋다.
            select 나 join은 괜찮음.
    Service는 다수의 DAO를 사용해도 괜찬음.
    Servlet도 다수의 Service를 사용해도 괜찮음.
 */

public class ManagerServiceImpl implements ManagerService {

    MemberDao memberDao;
    ManagerDao managerDao;
    PhotoDao photoDao;

    public void setMemberDao(MemberDao memberDao) {
        this.memberDao = memberDao;
    }

    public void setManagerDao(ManagerDao managerDao) {
        this.managerDao = managerDao;
    }

    public void setPhotoDao(PhotoDao photoDao) {
        this.photoDao = photoDao;
    }

    @Override
    public void add(Manager manager) {
        // 매니저 등록관 관련된 업무는 Service 객체에서 처리한다.
        memberDao.insert(manager);
        managerDao.insert(manager);

        if (manager.getPhoto() != null) {

            HashMap<String, Object> params = new HashMap<>();
            params.put("no", manager.getNo());
            params.put("photo", manager.getPhoto());
            //  photo의 독립성을 위해 hashmap객체를 만들어서 보냄.
            //  성능등의 단점도 있지만 소스코드 재 사용성에서 장점임.
            //  성능원하면 C쓰덩가!
            photoDao.insert(params);
        }
    }

    @Override
    public List<Manager> list(int pageNo, int pageSize) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("rowNo", (pageNo - 1) * pageSize);
        params.put("size", pageSize);

        return managerDao.findAll(params);
    }

    @Override
    public Manager get(int no) {
        return managerDao.findByNo(no);
    }

    @Override
    public void delete(int no) {
        if(managerDao.delete(no) == 0) {
            throw new RuntimeException("해당 번호의 데이터가 없습니다.");
        }
        photoDao.delete(no);
        memberDao.delete(no);
    }
}