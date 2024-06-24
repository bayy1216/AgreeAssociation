import { fakerKO as faker } from "@faker-js/faker";
import mysql from 'mysql2';

// MySQL 연결 설정
const connection = mysql.createConnection({
    host: 'localhost',
    user: 'root',
    password: 'root',
    database: 'agree'
});

const insertArticleAgree = () => {
    let articleAgrees = [];
    for (let i = 0; i < 1_000; i++) { // 예시로 200만건
        const articleId = faker.number.int({ min: 0, max: 100_0000 }); // 예시: article 테이블의 ID 범위에 맞게 설정
        const userId = faker.number.int({ min: 1, max: 9_9999 }); // 예시: users 테이블의 ID 범위에 맞게 설정
        articleAgrees.push([articleId, userId]);
    }

    const sql = 'INSERT INTO article_agree (article_id, user_id) VALUES ?';
    connection.query(sql, [articleAgrees], (err, results) => {
        if (err) {
            console.error('Error inserting data:', err);
        }
        console.log('Inserted ' + results.affectedRows + ' rows into article_agree table.');
    });
};


// 각 테이블에 데이터 삽입
connection.connect((err) => {
    if (err) {
        console.error('Error connecting to MySQL:', err);
    }
    console.log('Connected to MySQL database.');

    // 데이터 삽입 함수 호출
    for(let i = 0; i < 100; i++) {
        insertArticleAgree();

    }
    // insertArticleAgree2();
    // 다른 테이블 데이터 삽입 함수들도 추가할 수 있음

    // 연결 종료
    connection.end();
});
