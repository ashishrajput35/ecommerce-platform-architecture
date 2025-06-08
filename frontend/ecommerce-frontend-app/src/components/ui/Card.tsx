import React from 'react';
import styles from './Card.module.css';

type CardProps = {
  title: string;
  children?: React.ReactNode;
  
};

const Card: React.FC<CardProps> = ({ title,children }) => {
  return (
    <div className={styles.card}>
      <h3 className={styles.title}>{title}</h3>
      {/* <p className={styles.content}>{content}</p> */}
      {children}
    </div>
  );
};

export default Card;
