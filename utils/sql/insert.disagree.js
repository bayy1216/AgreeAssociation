import { fakerKO as faker } from "@faker-js/faker";
import mysql from 'mysql2';

// MySQL 연결 설정
const connection = mysql.createConnection({
    host: 'localhost',
    user: 'root',
    password: 'root',
    database: 'agree'
});

// article_disagree 테이블 데이터 생성 및 삽입
const insertArticleDisagree = () => {
    let articleDisagrees = [];
    for (let i = 0; i < 10000; i++) { // 예시로 50000개의 데이터 삽입
        const articleId = faker.number.int({ min: 0, max: 100_0000 }); // 예시: article 테이블의 ID 범위에 맞게 설정
        const userId = faker.number.int({ min: 0, max: 10_0000 }); // 예시: users 테이블의 ID 범위에 맞게 설정
        articleDisagrees.push([articleId, userId]);
    }

    const sql = 'INSERT INTO article_disagree (article_id, user_id) VALUES ?';
    connection.query(sql, [articleDisagrees], (err, results) => {
        if (err) throw err;
        console.log('Inserted ' + results.affectedRows + ' rows into article_disagree table.');
    });
};


// 각 테이블에 데이터 삽입
connection.connect((err) => {
    if (err) throw err;
    console.log('Connected to MySQL database.');

    // 데이터 삽입 함수 호출
    for(let i = 0; i < 5; i++) {
        insertArticleDisagree();
    }
    // 다른 테이블 데이터 삽입 함수들도 추가할 수 있음

    // 연결 종료
    connection.end();
});
