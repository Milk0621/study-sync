import { useEffect, useState } from 'react';
import api from '../../api/api';
import { useDispatch, useSelector } from 'react-redux';
import { useNavigate } from 'react-router-dom';
import { openModal } from '../../store/modalSlice';
import style from './MyGroup.module.css';
import MyGroupList from '../../components/MyGroupList/MyGroupList';

function MyGroup() {
  const user = useSelector((state) => state.user.user);
  const dispatch = useDispatch();
  const navigate = useNavigate();
  const [myGroups, setMyGroups] = useState([]);
  const [joinedGroups, setJoinedGroups] = useState([]);

  useEffect(() => {
    if (!user) return;

    const fetchMyGroups = async () => {
      const res = await api.get(`/groups/my`, {
        params: {userId: user.id}
      });
      const allGroups = res.data;
      
      //내가 만든 그룹
      const myGroups = allGroups.filter(group => group.createUser === user.id);
      //내가 참여중인 그룹
      const joinedGroups = allGroups.filter(group => group.createUser !== user.id);
      setMyGroups(myGroups);
      setJoinedGroups(joinedGroups);
    };

    fetchMyGroups();
  }, [user]);

  function loginCheck(){
    if(!user){
      dispatch(openModal());
    }else {
      navigate('/groupCreate');
    }
  }

  return (
    <div className='wrap'>
      <div className={style.myGroupTop}>
        <h4>내가 만든 그룹</h4>
        <button onClick={()=>loginCheck()}> + 그룹 만들기</button>
      </div>
      <MyGroupList 
        groups={myGroups}
        onClickGroup={(groupId) => navigate(`/group/${groupId}`)} 
      />

      <h4 style={{textAlign:'left'}}>참여 중인 그룹</h4>
      <MyGroupList 
        groups={joinedGroups}
        onClickGroup={(groupId) => navigate(`/group/${groupId}`)} 
      />
    </div>
  );
}

export default MyGroup;
