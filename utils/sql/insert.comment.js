import { fakerKO as faker } from "@faker-js/faker";
import mysql from 'mysql2';

// MySQL 연결 설정
const connection = mysql.createConnection({
    host: 'localhost',
    user: 'root',
    password: 'root',
    database: 'agree'
});


// article_comment 테이블 데이터 생성 및 삽입
const insertArticleComment = () => {
    let articleComments = [];
    for (let i = 0; i < 5000000; i++) { // 예시로 500만건
        const articleId = faker.number.int({ min: 1, max: 1_000_000 }); // 예시: article 테이블의 ID 범위에 맞게 설정
        const authorId = faker.number.int({ min: 0, max: 100_000 }); // 예시: users 테이블의 ID 범위에 맞게 설정
        const createdAt = faker.date.past();
        const modifiedAt = faker.date.recent();
        const content = faker.lorem.paragraph();
        const parentPath = ''; // 예시로 빈 문자열

        articleComments.push([createdAt, modifiedAt, content, parentPath, articleId, authorId]);
    }

    const sql = 'INSERT INTO article_comment (created_at, modified_at, content, parent_path, article_id, author_id) VALUES ?';
    connection.query(sql, [articleComments], (err, results) => {
        if (err) throw err;
        console.log('Inserted ' + results.affectedRows + ' rows into article_comment table.');
    });
};

// 각 테이블에 데이터 삽입
connection.connect((err) => {
    if (err) throw err;
    console.log('Connected to MySQL database.');

    // 데이터 삽입 함수 호출
    insertUsers();
    // 다른 테이블 데이터 삽입 함수들도 추가할 수 있음

    // 연결 종료
    connection.end();
});
