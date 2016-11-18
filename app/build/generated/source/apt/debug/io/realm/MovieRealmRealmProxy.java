package io.realm;


import android.annotation.TargetApi;
import android.os.Build;
import android.util.JsonReader;
import android.util.JsonToken;

import adrian.com.moodie.realm_objects.MovieRealm;
import io.realm.exceptions.RealmMigrationNeededException;
import io.realm.internal.ColumnInfo;
import io.realm.internal.RealmObjectProxy;
import io.realm.internal.Row;
import io.realm.internal.SharedRealm;
import io.realm.internal.Table;
import io.realm.internal.android.JsonUtils;
import io.realm.log.RealmLog;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

public class MovieRealmRealmProxy extends MovieRealm
    implements RealmObjectProxy, MovieRealmRealmProxyInterface {

    static final class MovieRealmColumnInfo extends ColumnInfo
        implements Cloneable {

        public long genreIndex;
        public long movieTitleIndex;
        public long posterIndex;

        MovieRealmColumnInfo(String path, Table table) {
            final Map<String, Long> indicesMap = new HashMap<String, Long>(3);
            this.genreIndex = getValidColumnIndex(path, table, "MovieRealm", "genre");
            indicesMap.put("genre", this.genreIndex);
            this.movieTitleIndex = getValidColumnIndex(path, table, "MovieRealm", "movieTitle");
            indicesMap.put("movieTitle", this.movieTitleIndex);
            this.posterIndex = getValidColumnIndex(path, table, "MovieRealm", "poster");
            indicesMap.put("poster", this.posterIndex);

            setIndicesMap(indicesMap);
        }

        @Override
        public final void copyColumnInfoFrom(ColumnInfo other) {
            final MovieRealmColumnInfo otherInfo = (MovieRealmColumnInfo) other;
            this.genreIndex = otherInfo.genreIndex;
            this.movieTitleIndex = otherInfo.movieTitleIndex;
            this.posterIndex = otherInfo.posterIndex;

            setIndicesMap(otherInfo.getIndicesMap());
        }

        @Override
        public final MovieRealmColumnInfo clone() {
            return (MovieRealmColumnInfo) super.clone();
        }

    }
    private MovieRealmColumnInfo columnInfo;
    private ProxyState proxyState;
    private static final List<String> FIELD_NAMES;
    static {
        List<String> fieldNames = new ArrayList<String>();
        fieldNames.add("genre");
        fieldNames.add("movieTitle");
        fieldNames.add("poster");
        FIELD_NAMES = Collections.unmodifiableList(fieldNames);
    }

    MovieRealmRealmProxy() {
        if (proxyState == null) {
            injectObjectContext();
        }
        proxyState.setConstructionFinished();
    }

    private void injectObjectContext() {
        final BaseRealm.RealmObjectContext context = BaseRealm.objectContext.get();
        this.columnInfo = (MovieRealmColumnInfo) context.getColumnInfo();
        this.proxyState = new ProxyState(MovieRealm.class, this);
        proxyState.setRealm$realm(context.getRealm());
        proxyState.setRow$realm(context.getRow());
        proxyState.setAcceptDefaultValue$realm(context.getAcceptDefaultValue());
        proxyState.setExcludeFields$realm(context.getExcludeFields());
    }

    @SuppressWarnings("cast")
    public int realmGet$genre() {
        if (proxyState == null) {
            // Called from model's constructor. Inject context.
            injectObjectContext();
        }

        proxyState.getRealm$realm().checkIfValid();
        return (int) proxyState.getRow$realm().getLong(columnInfo.genreIndex);
    }

    public void realmSet$genre(int value) {
        if (proxyState == null) {
            // Called from model's constructor. Inject context.
            injectObjectContext();
        }

        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            row.getTable().setLong(columnInfo.genreIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        proxyState.getRow$realm().setLong(columnInfo.genreIndex, value);
    }

    @SuppressWarnings("cast")
    public String realmGet$movieTitle() {
        if (proxyState == null) {
            // Called from model's constructor. Inject context.
            injectObjectContext();
        }

        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.movieTitleIndex);
    }

    public void realmSet$movieTitle(String value) {
        if (proxyState == null) {
            // Called from model's constructor. Inject context.
            injectObjectContext();
        }

        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.movieTitleIndex, row.getIndex(), true);
                return;
            }
            row.getTable().setString(columnInfo.movieTitleIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.movieTitleIndex);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.movieTitleIndex, value);
    }

    @SuppressWarnings("cast")
    public byte[] realmGet$poster() {
        if (proxyState == null) {
            // Called from model's constructor. Inject context.
            injectObjectContext();
        }

        proxyState.getRealm$realm().checkIfValid();
        return (byte[]) proxyState.getRow$realm().getBinaryByteArray(columnInfo.posterIndex);
    }

    public void realmSet$poster(byte[] value) {
        if (proxyState == null) {
            // Called from model's constructor. Inject context.
            injectObjectContext();
        }

        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.posterIndex, row.getIndex(), true);
                return;
            }
            row.getTable().setBinaryByteArray(columnInfo.posterIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.posterIndex);
            return;
        }
        proxyState.getRow$realm().setBinaryByteArray(columnInfo.posterIndex, value);
    }

    public static RealmObjectSchema createRealmObjectSchema(RealmSchema realmSchema) {
        if (!realmSchema.contains("MovieRealm")) {
            RealmObjectSchema realmObjectSchema = realmSchema.create("MovieRealm");
            realmObjectSchema.add(new Property("genre", RealmFieldType.INTEGER, !Property.PRIMARY_KEY, !Property.INDEXED, Property.REQUIRED));
            realmObjectSchema.add(new Property("movieTitle", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED));
            realmObjectSchema.add(new Property("poster", RealmFieldType.BINARY, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED));
            return realmObjectSchema;
        }
        return realmSchema.get("MovieRealm");
    }

    public static Table initTable(SharedRealm sharedRealm) {
        if (!sharedRealm.hasTable("class_MovieRealm")) {
            Table table = sharedRealm.getTable("class_MovieRealm");
            table.addColumn(RealmFieldType.INTEGER, "genre", Table.NOT_NULLABLE);
            table.addColumn(RealmFieldType.STRING, "movieTitle", Table.NULLABLE);
            table.addColumn(RealmFieldType.BINARY, "poster", Table.NULLABLE);
            table.setPrimaryKey("");
            return table;
        }
        return sharedRealm.getTable("class_MovieRealm");
    }

    public static MovieRealmColumnInfo validateTable(SharedRealm sharedRealm, boolean allowExtraColumns) {
        if (sharedRealm.hasTable("class_MovieRealm")) {
            Table table = sharedRealm.getTable("class_MovieRealm");
            final long columnCount = table.getColumnCount();
            if (columnCount != 3) {
                if (columnCount < 3) {
                    throw new RealmMigrationNeededException(sharedRealm.getPath(), "Field count is less than expected - expected 3 but was " + columnCount);
                }
                if (allowExtraColumns) {
                    RealmLog.debug("Field count is more than expected - expected 3 but was %1$d", columnCount);
                } else {
                    throw new RealmMigrationNeededException(sharedRealm.getPath(), "Field count is more than expected - expected 3 but was " + columnCount);
                }
            }
            Map<String, RealmFieldType> columnTypes = new HashMap<String, RealmFieldType>();
            for (long i = 0; i < columnCount; i++) {
                columnTypes.put(table.getColumnName(i), table.getColumnType(i));
            }

            final MovieRealmColumnInfo columnInfo = new MovieRealmColumnInfo(sharedRealm.getPath(), table);

            if (!columnTypes.containsKey("genre")) {
                throw new RealmMigrationNeededException(sharedRealm.getPath(), "Missing field 'genre' in existing Realm file. Either remove field or migrate using io.realm.internal.Table.addColumn().");
            }
            if (columnTypes.get("genre") != RealmFieldType.INTEGER) {
                throw new RealmMigrationNeededException(sharedRealm.getPath(), "Invalid type 'int' for field 'genre' in existing Realm file.");
            }
            if (table.isColumnNullable(columnInfo.genreIndex)) {
                throw new RealmMigrationNeededException(sharedRealm.getPath(), "Field 'genre' does support null values in the existing Realm file. Use corresponding boxed type for field 'genre' or migrate using RealmObjectSchema.setNullable().");
            }
            if (!columnTypes.containsKey("movieTitle")) {
                throw new RealmMigrationNeededException(sharedRealm.getPath(), "Missing field 'movieTitle' in existing Realm file. Either remove field or migrate using io.realm.internal.Table.addColumn().");
            }
            if (columnTypes.get("movieTitle") != RealmFieldType.STRING) {
                throw new RealmMigrationNeededException(sharedRealm.getPath(), "Invalid type 'String' for field 'movieTitle' in existing Realm file.");
            }
            if (!table.isColumnNullable(columnInfo.movieTitleIndex)) {
                throw new RealmMigrationNeededException(sharedRealm.getPath(), "Field 'movieTitle' is required. Either set @Required to field 'movieTitle' or migrate using RealmObjectSchema.setNullable().");
            }
            if (!columnTypes.containsKey("poster")) {
                throw new RealmMigrationNeededException(sharedRealm.getPath(), "Missing field 'poster' in existing Realm file. Either remove field or migrate using io.realm.internal.Table.addColumn().");
            }
            if (columnTypes.get("poster") != RealmFieldType.BINARY) {
                throw new RealmMigrationNeededException(sharedRealm.getPath(), "Invalid type 'byte[]' for field 'poster' in existing Realm file.");
            }
            if (!table.isColumnNullable(columnInfo.posterIndex)) {
                throw new RealmMigrationNeededException(sharedRealm.getPath(), "Field 'poster' is required. Either set @Required to field 'poster' or migrate using RealmObjectSchema.setNullable().");
            }
            return columnInfo;
        } else {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "The 'MovieRealm' class is missing from the schema for this Realm.");
        }
    }

    public static String getTableName() {
        return "class_MovieRealm";
    }

    public static List<String> getFieldNames() {
        return FIELD_NAMES;
    }

    @SuppressWarnings("cast")
    public static MovieRealm createOrUpdateUsingJsonObject(Realm realm, JSONObject json, boolean update)
        throws JSONException {
        final List<String> excludeFields = Collections.<String> emptyList();
        MovieRealm obj = realm.createObjectInternal(MovieRealm.class, true, excludeFields);
        if (json.has("genre")) {
            if (json.isNull("genre")) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'genre' to null.");
            } else {
                ((MovieRealmRealmProxyInterface) obj).realmSet$genre((int) json.getInt("genre"));
            }
        }
        if (json.has("movieTitle")) {
            if (json.isNull("movieTitle")) {
                ((MovieRealmRealmProxyInterface) obj).realmSet$movieTitle(null);
            } else {
                ((MovieRealmRealmProxyInterface) obj).realmSet$movieTitle((String) json.getString("movieTitle"));
            }
        }
        if (json.has("poster")) {
            if (json.isNull("poster")) {
                ((MovieRealmRealmProxyInterface) obj).realmSet$poster(null);
            } else {
                ((MovieRealmRealmProxyInterface) obj).realmSet$poster(JsonUtils.stringToBytes(json.getString("poster")));
            }
        }
        return obj;
    }

    @SuppressWarnings("cast")
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static MovieRealm createUsingJsonStream(Realm realm, JsonReader reader)
        throws IOException {
        MovieRealm obj = new MovieRealm();
        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (name.equals("genre")) {
                if (reader.peek() == JsonToken.NULL) {
                    reader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'genre' to null.");
                } else {
                    ((MovieRealmRealmProxyInterface) obj).realmSet$genre((int) reader.nextInt());
                }
            } else if (name.equals("movieTitle")) {
                if (reader.peek() == JsonToken.NULL) {
                    reader.skipValue();
                    ((MovieRealmRealmProxyInterface) obj).realmSet$movieTitle(null);
                } else {
                    ((MovieRealmRealmProxyInterface) obj).realmSet$movieTitle((String) reader.nextString());
                }
            } else if (name.equals("poster")) {
                if (reader.peek() == JsonToken.NULL) {
                    reader.skipValue();
                    ((MovieRealmRealmProxyInterface) obj).realmSet$poster(null);
                } else {
                    ((MovieRealmRealmProxyInterface) obj).realmSet$poster(JsonUtils.stringToBytes(reader.nextString()));
                }
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        obj = realm.copyToRealm(obj);
        return obj;
    }

    public static MovieRealm copyOrUpdate(Realm realm, MovieRealm object, boolean update, Map<RealmModel,RealmObjectProxy> cache) {
        if (object instanceof RealmObjectProxy && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().threadId != realm.threadId) {
            throw new IllegalArgumentException("Objects which belong to Realm instances in other threads cannot be copied into this Realm instance.");
        }
        if (object instanceof RealmObjectProxy && ((RealmObjectProxy)object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy)object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
            return object;
        }
        final BaseRealm.RealmObjectContext objectContext = BaseRealm.objectContext.get();
        RealmObjectProxy cachedRealmObject = cache.get(object);
        if (cachedRealmObject != null) {
            return (MovieRealm) cachedRealmObject;
        } else {
            return copy(realm, object, update, cache);
        }
    }

    public static MovieRealm copy(Realm realm, MovieRealm newObject, boolean update, Map<RealmModel,RealmObjectProxy> cache) {
        RealmObjectProxy cachedRealmObject = cache.get(newObject);
        if (cachedRealmObject != null) {
            return (MovieRealm) cachedRealmObject;
        } else {
            // rejecting default values to avoid creating unexpected objects from RealmModel/RealmList fields.
            MovieRealm realmObject = realm.createObjectInternal(MovieRealm.class, false, Collections.<String>emptyList());
            cache.put(newObject, (RealmObjectProxy) realmObject);
            ((MovieRealmRealmProxyInterface) realmObject).realmSet$genre(((MovieRealmRealmProxyInterface) newObject).realmGet$genre());
            ((MovieRealmRealmProxyInterface) realmObject).realmSet$movieTitle(((MovieRealmRealmProxyInterface) newObject).realmGet$movieTitle());
            ((MovieRealmRealmProxyInterface) realmObject).realmSet$poster(((MovieRealmRealmProxyInterface) newObject).realmGet$poster());
            return realmObject;
        }
    }

    public static long insert(Realm realm, MovieRealm object, Map<RealmModel,Long> cache) {
        if (object instanceof RealmObjectProxy && ((RealmObjectProxy)object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy)object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
            return ((RealmObjectProxy)object).realmGet$proxyState().getRow$realm().getIndex();
        }
        Table table = realm.getTable(MovieRealm.class);
        long tableNativePtr = table.getNativeTablePointer();
        MovieRealmColumnInfo columnInfo = (MovieRealmColumnInfo) realm.schema.getColumnInfo(MovieRealm.class);
        long rowIndex = Table.nativeAddEmptyRow(tableNativePtr, 1);
        cache.put(object, rowIndex);
        Table.nativeSetLong(tableNativePtr, columnInfo.genreIndex, rowIndex, ((MovieRealmRealmProxyInterface)object).realmGet$genre(), false);
        String realmGet$movieTitle = ((MovieRealmRealmProxyInterface)object).realmGet$movieTitle();
        if (realmGet$movieTitle != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.movieTitleIndex, rowIndex, realmGet$movieTitle, false);
        }
        byte[] realmGet$poster = ((MovieRealmRealmProxyInterface)object).realmGet$poster();
        if (realmGet$poster != null) {
            Table.nativeSetByteArray(tableNativePtr, columnInfo.posterIndex, rowIndex, realmGet$poster, false);
        }
        return rowIndex;
    }

    public static void insert(Realm realm, Iterator<? extends RealmModel> objects, Map<RealmModel,Long> cache) {
        Table table = realm.getTable(MovieRealm.class);
        long tableNativePtr = table.getNativeTablePointer();
        MovieRealmColumnInfo columnInfo = (MovieRealmColumnInfo) realm.schema.getColumnInfo(MovieRealm.class);
        MovieRealm object = null;
        while (objects.hasNext()) {
            object = (MovieRealm) objects.next();
            if(!cache.containsKey(object)) {
                if (object instanceof RealmObjectProxy && ((RealmObjectProxy)object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy)object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                    cache.put(object, ((RealmObjectProxy)object).realmGet$proxyState().getRow$realm().getIndex());
                    continue;
                }
                long rowIndex = Table.nativeAddEmptyRow(tableNativePtr, 1);
                cache.put(object, rowIndex);
                Table.nativeSetLong(tableNativePtr, columnInfo.genreIndex, rowIndex, ((MovieRealmRealmProxyInterface)object).realmGet$genre(), false);
                String realmGet$movieTitle = ((MovieRealmRealmProxyInterface)object).realmGet$movieTitle();
                if (realmGet$movieTitle != null) {
                    Table.nativeSetString(tableNativePtr, columnInfo.movieTitleIndex, rowIndex, realmGet$movieTitle, false);
                }
                byte[] realmGet$poster = ((MovieRealmRealmProxyInterface)object).realmGet$poster();
                if (realmGet$poster != null) {
                    Table.nativeSetByteArray(tableNativePtr, columnInfo.posterIndex, rowIndex, realmGet$poster, false);
                }
            }
        }
    }

    public static long insertOrUpdate(Realm realm, MovieRealm object, Map<RealmModel,Long> cache) {
        if (object instanceof RealmObjectProxy && ((RealmObjectProxy)object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy)object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
            return ((RealmObjectProxy)object).realmGet$proxyState().getRow$realm().getIndex();
        }
        Table table = realm.getTable(MovieRealm.class);
        long tableNativePtr = table.getNativeTablePointer();
        MovieRealmColumnInfo columnInfo = (MovieRealmColumnInfo) realm.schema.getColumnInfo(MovieRealm.class);
        long rowIndex = Table.nativeAddEmptyRow(tableNativePtr, 1);
        cache.put(object, rowIndex);
        Table.nativeSetLong(tableNativePtr, columnInfo.genreIndex, rowIndex, ((MovieRealmRealmProxyInterface)object).realmGet$genre(), false);
        String realmGet$movieTitle = ((MovieRealmRealmProxyInterface)object).realmGet$movieTitle();
        if (realmGet$movieTitle != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.movieTitleIndex, rowIndex, realmGet$movieTitle, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.movieTitleIndex, rowIndex, false);
        }
        byte[] realmGet$poster = ((MovieRealmRealmProxyInterface)object).realmGet$poster();
        if (realmGet$poster != null) {
            Table.nativeSetByteArray(tableNativePtr, columnInfo.posterIndex, rowIndex, realmGet$poster, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.posterIndex, rowIndex, false);
        }
        return rowIndex;
    }

    public static void insertOrUpdate(Realm realm, Iterator<? extends RealmModel> objects, Map<RealmModel,Long> cache) {
        Table table = realm.getTable(MovieRealm.class);
        long tableNativePtr = table.getNativeTablePointer();
        MovieRealmColumnInfo columnInfo = (MovieRealmColumnInfo) realm.schema.getColumnInfo(MovieRealm.class);
        MovieRealm object = null;
        while (objects.hasNext()) {
            object = (MovieRealm) objects.next();
            if(!cache.containsKey(object)) {
                if (object instanceof RealmObjectProxy && ((RealmObjectProxy)object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy)object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                    cache.put(object, ((RealmObjectProxy)object).realmGet$proxyState().getRow$realm().getIndex());
                    continue;
                }
                long rowIndex = Table.nativeAddEmptyRow(tableNativePtr, 1);
                cache.put(object, rowIndex);
                Table.nativeSetLong(tableNativePtr, columnInfo.genreIndex, rowIndex, ((MovieRealmRealmProxyInterface)object).realmGet$genre(), false);
                String realmGet$movieTitle = ((MovieRealmRealmProxyInterface)object).realmGet$movieTitle();
                if (realmGet$movieTitle != null) {
                    Table.nativeSetString(tableNativePtr, columnInfo.movieTitleIndex, rowIndex, realmGet$movieTitle, false);
                } else {
                    Table.nativeSetNull(tableNativePtr, columnInfo.movieTitleIndex, rowIndex, false);
                }
                byte[] realmGet$poster = ((MovieRealmRealmProxyInterface)object).realmGet$poster();
                if (realmGet$poster != null) {
                    Table.nativeSetByteArray(tableNativePtr, columnInfo.posterIndex, rowIndex, realmGet$poster, false);
                } else {
                    Table.nativeSetNull(tableNativePtr, columnInfo.posterIndex, rowIndex, false);
                }
            }
        }
    }

    public static MovieRealm createDetachedCopy(MovieRealm realmObject, int currentDepth, int maxDepth, Map<RealmModel, CacheData<RealmModel>> cache) {
        if (currentDepth > maxDepth || realmObject == null) {
            return null;
        }
        CacheData<RealmModel> cachedObject = cache.get(realmObject);
        MovieRealm unmanagedObject;
        if (cachedObject != null) {
            // Reuse cached object or recreate it because it was encountered at a lower depth.
            if (currentDepth >= cachedObject.minDepth) {
                return (MovieRealm)cachedObject.object;
            } else {
                unmanagedObject = (MovieRealm)cachedObject.object;
                cachedObject.minDepth = currentDepth;
            }
        } else {
            unmanagedObject = new MovieRealm();
            cache.put(realmObject, new RealmObjectProxy.CacheData(currentDepth, unmanagedObject));
        }
        ((MovieRealmRealmProxyInterface) unmanagedObject).realmSet$genre(((MovieRealmRealmProxyInterface) realmObject).realmGet$genre());
        ((MovieRealmRealmProxyInterface) unmanagedObject).realmSet$movieTitle(((MovieRealmRealmProxyInterface) realmObject).realmGet$movieTitle());
        ((MovieRealmRealmProxyInterface) unmanagedObject).realmSet$poster(((MovieRealmRealmProxyInterface) realmObject).realmGet$poster());
        return unmanagedObject;
    }

    @Override
    public String toString() {
        if (!RealmObject.isValid(this)) {
            return "Invalid object";
        }
        StringBuilder stringBuilder = new StringBuilder("MovieRealm = [");
        stringBuilder.append("{genre:");
        stringBuilder.append(realmGet$genre());
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{movieTitle:");
        stringBuilder.append(realmGet$movieTitle() != null ? realmGet$movieTitle() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{poster:");
        stringBuilder.append(realmGet$poster() != null ? realmGet$poster() : "null");
        stringBuilder.append("}");
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    @Override
    public ProxyState realmGet$proxyState() {
        return proxyState;
    }

    @Override
    public int hashCode() {
        String realmName = proxyState.getRealm$realm().getPath();
        String tableName = proxyState.getRow$realm().getTable().getName();
        long rowIndex = proxyState.getRow$realm().getIndex();

        int result = 17;
        result = 31 * result + ((realmName != null) ? realmName.hashCode() : 0);
        result = 31 * result + ((tableName != null) ? tableName.hashCode() : 0);
        result = 31 * result + (int) (rowIndex ^ (rowIndex >>> 32));
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MovieRealmRealmProxy aMovieRealm = (MovieRealmRealmProxy)o;

        String path = proxyState.getRealm$realm().getPath();
        String otherPath = aMovieRealm.proxyState.getRealm$realm().getPath();
        if (path != null ? !path.equals(otherPath) : otherPath != null) return false;

        String tableName = proxyState.getRow$realm().getTable().getName();
        String otherTableName = aMovieRealm.proxyState.getRow$realm().getTable().getName();
        if (tableName != null ? !tableName.equals(otherTableName) : otherTableName != null) return false;

        if (proxyState.getRow$realm().getIndex() != aMovieRealm.proxyState.getRow$realm().getIndex()) return false;

        return true;
    }

}
