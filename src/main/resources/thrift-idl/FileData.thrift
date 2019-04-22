namespace java com.aries.phoenix.model

struct FileData{
    1:required string name,
    2:required binary data,
    3:required i32 size
}

struct Response{
    1:required i32 code;
    2:optional string name,
    3:optional binary data,
    4:optional i64 size,
    5:optional string format,
}

service FileUploadService {
    i32 uploadFile(1:FileData data);
    Response getFileById(1:i64 id);
    Response getPhotoById(1:i64 id);
}
