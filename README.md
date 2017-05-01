# file-api-demo2

# Spring boot based, gradle-based spring application which can be imported to eclipse or gradle ide and run :bootRun command to start file upload application.

description: spring mvc rest file upload service demo - version 2



# To Upload any file upto size 1 M, use below url locally,

http://localhost:8080/

Uploads are done under root context. '/'

Alternative way to upload file is to make POST call on same root context,

POST http://localhost:8080/

with form-data type set as body, multi-part key name as 'file' and part type as 'file' and attach file to upload via chrome plugin like postman





# to see all uploaded files metadata, use below API

GET localhost:8080/files


# to see specific uploaded file's metadata, use below API

GET localhost:8080/files/1

# to get specific uploaded file's contents, use below API

GET localhost:8080/files/1/data




