namespace java com.aries.phoenix.model

struct FileData{
    1:required string name,
    2:required binary data,
    3:required i32 size
}

struct FileResponse{
    1:optional string name,
    2:optional binary data,
    3:optional i64 size,
    4:optional string format,
}

struct PhoenixResponse{
    1:required i32 code,
    2:optional string msg="",
    3:optional FileResponse fileResponse,
}

service FileUploadService {
    i64 uploadFile(1:FileData data);
    PhoenixResponse getFileById(1:i64 id);
    PhoenixResponse getPhotoById(1:i64 id);
}
