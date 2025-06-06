# 自行车管理系统 API 接口文档

## 目录
1. [用户管理接口](#1-用户管理接口)
2. [自行车管理接口](#2-自行车管理接口)
3. [停车位管理接口](#3-停车位管理接口)
4. [记录管理接口](#4-记录管理接口)

## 1. 用户管理接口

### 1.1 查看当前用户
- **URL**: `/user`
- **方法**: `GET`
- **描述**: 获取当前登录用户的详细信息
- **请求头**:
  - `Authorization`: Bearer {token} (JWT令牌)
- **响应**:
  - 成功: 200 OK
    ```json
    {
      "user": {
        "userid": 1,
        "username": "用户名",
        "logintext": "登录账号",
        "password": "密码(已加密)",
        "voice": "语音特征",
        "hand": "手势特征",
        "avatar": "头像URL",
        "usebikeid": 0,
        "nowborrow": 0
      }
    }
    ```
  - 失败: 401 Unauthorized

### 1.2 用户账号登录
- **URL**: `/user/login`
- **方法**: `POST`
- **描述**: 使用账号密码登录系统
- **请求体**:
  ```json
  {
    "loginText": "登录账号",
    "password": "密码"
  }
  ```
- **响应**:
  - 成功: 200 OK
    ```json
    {
      "token": "JWT令牌",
      "user": {
        "userid": 1,
        "username": "用户名",
        "logintext": "登录账号",
        "password": "密码(已加密)",
        "voice": "语音特征",
        "hand": "手势特征",
        "avatar": "头像URL",
        "usebikeid": 0,
        "nowborrow": 0
      }
    }
    ```
  - 失败: 401 Unauthorized
    ```
    用户名或密码错误
    ```

### 1.3 用户人脸登录
- **URL**: `/user/loginByFace`
- **方法**: `POST`
- **描述**: 使用人脸识别登录系统
- **请求体**:
  ```
  用户ID (整数)
  ```
- **响应**:
  - 成功: 200 OK
    ```json
    {
      "token": "JWT令牌",
      "user": {
        "userid": 1,
        "username": "用户名",
        "logintext": "登录账号",
        "password": "密码(已加密)",
        "voice": "语音特征",
        "hand": "手势特征",
        "avatar": "头像URL",
        "usebikeid": 0,
        "nowborrow": 0
      }
    }
    ```
  - 失败: 401 Unauthorized
    ```
    用户名或密码错误
    ```

### 1.4 更新用户信息
- **URL**: `/user/update`
- **方法**: `POST`
- **描述**: 更新用户信息
- **请求头**:
  - `Authorization`: Bearer {token} (JWT令牌)
- **请求体**:
  ```json
  {
    "userid": 1,
    "username": "新用户名",
    "logintext": "新登录账号",
    "password": "新密码",
    "voice": "新语音特征",
    "hand": "新手势特征",
    "avatar": "新头像URL",
    "usebikeid": 0,
    "nowborrow": 0
  }
  ```
- **响应**:
  - 成功: 200 OK
    ```
    1
    ```
  - 失败: 200 OK
    ```
    0
    ```

### 1.5 注册用户信息
- **URL**: `/user/register`
- **方法**: `POST`
- **描述**: 注册新用户
- **请求体**:
  ```json
  {
    "loginText": "登录账号",
    "password": "密码"
  }
  ```
- **响应**:
  - 成功: 200 OK
    ```
    1
    ```
  - 失败: 200 OK
    ```
    0
    ```

### 1.6 删除用户信息
- **URL**: `/user/delete`
- **方法**: `POST`
- **描述**: 删除用户
- **请求头**:
  - `Authorization`: Bearer {token} (JWT令牌)
- **请求体**:
  ```
  用户ID (整数)
  ```
- **响应**:
  - 成功: 200 OK
    ```
    1
    ```
  - 失败: 200 OK
    ```
    0
    ```

## 2. 自行车管理接口

### 2.1 查看自行车
- **URL**: `/bike`
- **方法**: `GET`
- **描述**: 获取当前用户拥有的自行车列表
- **请求头**:
  - `Authorization`: Bearer {token} (JWT令牌)
- **响应**:
  - 成功: 200 OK
    ```json
    [
      {
        "bikeid": 1,
        "status": 0,
        "type": "private",
        "bikename": "自行车名称",
        "userid": 0,
        "parkingid": 1,
        "ownerid": 1
      }
    ]
    ```

### 2.2 添加自行车
- **URL**: `/bike/add`
- **方法**: `POST`
- **描述**: 添加新自行车
- **请求头**:
  - `Authorization`: Bearer {token} (JWT令牌)
- **请求体**:
  ```json
  {
    "status": 0,
    "type": "private",
    "bikename": "自行车名称"
  }
  ```
- **响应**:
  - 成功: 200 OK
    ```
    添加成功, bikeId: 1
    ```
  - 失败: 400 Bad Request
    ```
    添加失败.
    ```

### 2.3 更新自行车
- **URL**: `/bike/update`
- **方法**: `PUT`
- **描述**: 更新自行车信息
- **请求头**:
  - `Authorization`: Bearer {token} (JWT令牌)
- **请求体**:
  ```json
  {
    "bikeid": 1,
    "status": 0,
    "type": "share",
    "bikename": "新自行车名称",
    "userid": 0,
    "parkingid": 1,
    "ownerid": 1
  }
  ```
- **响应**:
  - 成功: 200 OK
    ```json
    {
      "bikeid": 1,
      "status": 0,
      "type": "share",
      "bikename": "新自行车名称",
      "userid": 0,
      "parkingid": 1,
      "ownerid": 1
    }
    ```
  - 失败: 400 Bad Request
    ```
    更新失败.
    ```
    或
    ```
    更新失败, 车辆不属于您.
    ```

### 2.4 删除自行车
- **URL**: `/bike/delete`
- **方法**: `DELETE`
- **描述**: 删除自行车
- **请求头**:
  - `Authorization`: Bearer {token} (JWT令牌)
- **请求参数**:
  - `id`: 自行车ID
- **响应**:
  - 成功: 200 OK
    ```
    删除成功.
    ```
  - 失败: 400 Bad Request
    ```
    删除失败.
    ```
    或
    ```
    删除失败, 车辆不属于您.
    ```

## 3. 停车位管理接口

### 3.1 查找所有车位
- **URL**: `/parking`
- **方法**: `GET`
- **描述**: 获取指定组ID的停车位列表
- **请求参数**:
  - `groupId`: 组ID
- **响应**:
  - 成功: 200 OK
    ```json
    [
      {
        "parkingid": 1,
        "bikeid": 0,
        "status": 0,
        "recordid": 0
      }
    ]
    ```

### 3.2 更新停车位
- **URL**: `/parking/update`
- **方法**: `PUT`
- **描述**: 更新停车位信息
- **请求参数**:
  - `parking`: 停车位对象
- **响应**:
  - 成功: 200 OK
    ```
    1
    ```
  - 失败: 200 OK
    ```
    0
    ```

### 3.3 存车/还车
- **URL**: `/parking/park`
- **方法**: `POST`
- **描述**: 存车或还车操作
- **请求头**:
  - `Authorization`: Bearer {token} (JWT令牌)
- **请求体**:
  ```json
  {
    "parkingId": 1,
    "bikeId": 1
  }
  ```
- **响应**:
  - 成功: 200 OK
    ```
    操作成功.
    ```
  - 失败: 400 Bad Request
    ```
    操作失败, 车位已满.
    ```
    或
    ```
    操作失败.
    ```

### 3.4 取车/借车
- **URL**: `/parking/borrow`
- **方法**: `POST`
- **描述**: 取车或借车操作
- **请求头**:
  - `Authorization`: Bearer {token} (JWT令牌)
- **请求体**:
  ```json
  {
    "parkingId": 1,
    "bikeId": 1
  }
  ```
- **响应**:
  - 成功: 200 OK
    ```
    1
    ```
  - 失败: 400 Bad Request
    ```
    操作失败, 还有自行车未还.
    ```
    或
    ```
    操作失败, 车位为空.
    ```
    或
    ```
    操作失败, 自行车还有订单.
    ```
    或
    ```
    操作失败, 自行车不可取.
    ```
    或
    ```
    操作失败.
    ```

## 4. 记录管理接口

### 4.1 查询订单
- **URL**: `/record`
- **方法**: `GET`
- **描述**: 获取当前用户的订单记录
- **请求头**:
  - `Authorization`: Bearer {token} (JWT令牌)
- **响应**:
  - 成功: 200 OK
    ```json
    [
      {
        "recordid": 1,
        "bikeid": 1,
        "userid": 1,
        "type": "borrow",
        "starttime": "2023-01-01 12:00:00",
        "endtime": "2023-01-01 13:00:00",
        "money": 5.00,
        "sparkingid": 1,
        "eparkingid": 2,
        "touser": 0
      }
    ]
    ```