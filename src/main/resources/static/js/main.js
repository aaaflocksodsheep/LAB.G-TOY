async function httpRequest(url, method = 'GET', data = null, headers = {}) {
    // 기본 옵션 설정
    const options = {
        method,
        headers: {
            'Content-Type': 'application/json',
            ...headers,
        },
    };

    // 데이터가 있을 경우 바디에 JSON 문자열로 변환하여 추가
    if (data) {
        options.body = JSON.stringify(data);
    }

    try {
        const response = await fetch(url, options);
        // JSON 형태로 응답을 파싱
        const result = await response.json();

        // 응답 상태가 성공적이지 않다면 에러 던지기
        if (!response.ok) {
            throw new Error(result.message || 'HTTP 요청 실패');
        }

        return result;
    } catch (error) {
        // 에러 처리
        console.error('HTTP 요청 에러:', error);
        throw error;
    }
}

// 사용 예시
// GET 요청
httpRequest('https://api.example.com/data')
    .then(response => console.log(response))
    .catch(error => console.error(error));

// POST 요청
httpRequest('https://api.example.com/data', 'POST', { key: 'value' })
    .then(response => console.log(response))
    .catch(error => console.error(error));
