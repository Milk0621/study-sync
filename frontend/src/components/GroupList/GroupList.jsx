import { useEffect, useState } from 'react';
import style from './GroupList.module.css';
import api from '../../api/api';
import { useNavigate } from 'react-router-dom';
import { useDispatch, useSelector } from 'react-redux';
import { addScrap, removeScrap } from '../../store/scrapSlice';
import axios from 'axios';

function GroupList({groups, category}){
  const navigate = useNavigate();
  const scrapList = useSelector((state)=>state.scrap.scrapList);
  const dispatch = useDispatch();

  const toggleScrap = async (e, groupId) => {
    e.stopPropagation();

    const token = localStorage.getItem("token");
    if (!token) {
      alert("로그인이 필요합니다.");
      return;
    }

    if (scrapList.includes(groupId)) {
      await api.delete(`/groupScrap/${groupId}`, {
        headers: { Authorization: `Bearer ${token}` }
      });
      dispatch(removeScrap(groupId));
    } else {
      await api.post(`/groupScrap/${groupId}`, {}, {
        headers: { Authorization: `Bearer ${token}` }
      });
      dispatch(addScrap(groupId));
    }
  }

  return(
    <>
      { groups.map((group)=>{
        const scrapped = scrapList.includes(group.id);
        return(
          <div 
            key={group.id} 
            className={style.groupListBg} 
            onClick={()=>{
              if(category){
                navigate(`/post/${group.id}?category=${category}`)
              } else {
                navigate(`/post/${group.id}`)
              }
            }}
          >
            <span className={style.groupInfo}> {group.createUser} · 조회 {group.hit} · {group.createDate} </span>
            <button className={style.scrapBtn} onClick={(e)=>toggleScrap(e, group.id)}>
              {scrapped ? "★" : "☆"}
            </button>
            <h5 style={{fontWeight: '600'}}> {group.groupName} </h5>
            <p className={`${style.contentLineClamp}`}> {group.content} </p>
            <span className={style.hashTag}>{group.tag}</span>
          </div>
        )
      })}
    </>
  )
}

export default GroupList;