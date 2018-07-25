package oldmultimedia;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.google.common.io.ByteSource;
import com.google.common.io.Files;
import org.jclouds.io.Payload;
import org.jclouds.io.Payloads;
import org.jclouds.openstack.swift.v1.domain.ObjectList;
import org.jclouds.openstack.swift.v1.features.ObjectApi;
import org.jclouds.openstack.swift.v1.options.ListContainerOptions;
import org.jclouds.openstack.swift.v1.options.PutOptions;
import org.jclouds.rackspace.cloudfiles.v1.domain.CDNContainer;

import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Yenny Fung on 14/11/17.
 *
 * reference: https://developer.rackspace.com/docs/cloud-files/quickstart/?lang=java
 *
 */
public class RackspaceCloudFiles {
    private static String provider;
    private static String username;
    private static String apiKey;
    private static String region;
    private static String container;

    private static ObjectApi objectApi;
    private static CDNContainer cdnContainer;

//    @Inject
//    public RackspaceCloudFiles(Config config) {
//        Config configRackspace = config.getConfig("play.rackspace");
//        provider = configRackspace.getString("provider");
//        username = configRackspace.getString("username");
//        apiKey = configRackspace.getString("apiKey");
//        region = configRackspace.getString("region");
//        container = configRackspace.getString("cloudFiles.container");
//
//        try {
//            CloudFilesApi cloudFilesApi = ContextBuilder.newBuilder(provider)
//                    .credentials(username, apiKey)
//                    .buildApi(CloudFilesApi.class);
//
//            // no sirve porque no lo crea público
//            /*ContainerApi containerApi = cloudFilesApi.getContainerApi(region);
//            if (containerApi.get(container) == null) {// if the container does not exist, it creates it
//                containerApi.create(container);
//            }*/
//
//            objectApi = cloudFilesApi.getObjectApi(region, container);
//
//            CDNApi cdnApi = cloudFilesApi.getCDNApi(region);
//            cdnContainer = cdnApi.get(container);
//
//            System.out.println("*** Authentication to Rackspace Jclouds success...");
//        }catch (Exception e){
//            System.out.println("*** Authentication to Rackspace Jclouds failed...");
//            e.printStackTrace();
//        }
//    }

    public static String uploadObjectsToContainer(File file, String objectName) throws IOException {
        PutOptions putOptions = new PutOptions();
        Multimap<String, String> corsHeader = ImmutableMultimap.of("Access-Control-Allow-Origin", "*");
        putOptions.headers(corsHeader);

        String mimeType = MultimediaUtils.guessMIMEtype(file);
        if(mimeType != null) {
            Multimap<String, String> contentTypeHeader = ImmutableMultimap.of("Content-MediaType", mimeType); // como sale en la docuemntacion oficial no funciona --> objectApi.updateHeaders(objectName, ImmutableMap.of("Content-MediaType", "image/png"));
            putOptions.headers(contentTypeHeader);
        }

        ByteSource byteSource = Files.asByteSource(file);
        Payload filePayload = Payloads.newByteSourcePayload(byteSource);

        objectApi.put(objectName, filePayload, putOptions);

        return buildAbsolutePath(objectName);
    }

    public static String uploadObjectsToContainer(byte[] bytes, String objectName) throws IOException {
        File file = MultimediaUtils.bytesToFile(bytes);
        String path = uploadObjectsToContainer(file, objectName);
        file.delete();
        return path;
    }

    public static String uploadObjectsToContainer(BufferedImage image, String formatName, String objectName) throws IOException {
        File file = MultimediaUtils.imageToFile(image, formatName);
        String path = uploadObjectsToContainer(file, objectName);
        file.delete();
        return path;
    }

    private static String buildAbsolutePath(String relativePath){
        StringBuilder absolutePath = new StringBuilder();
        absolutePath.append(getObjectViaCDNsslUri().toString());    // https://host.ssl.cf1.rackcdn.com
        absolutePath.append("/");                                   // https://host.ssl.cf1.rackcdn.com/
        absolutePath.append(relativePath);                          // https://host.ssl.cf1.rackcdn.com/directory/randomUUID_filename.ext
        return absolutePath.toString();
    }

    public static void deleteObjectsToContainer(String objectName){
        objectApi.delete(objectName);
    }

    public static void deleteObjectsToContainer(List<String> objectNames){
        for(String objectName: objectNames) {
            objectApi.delete(objectName);
        }
    }

    public static void deleteObjectsUselessToContainer(List<String> safeObjectsNames){
        Collection<String> objectsNames = new ArrayList();
        ObjectList objects = objectApi.list();
        for(int i = 0; i < objects.size(); ++i){
            objectsNames.add(objects.get(i).getName());
        }
        objectsNames.removeAll(safeObjectsNames);
        for(String objectsName: objectsNames){
            objectApi.delete(objectsName);
        }
    }

    public static void deleteObjectsTest(){
        ListContainerOptions listContainerOptions = new ListContainerOptions();
        listContainerOptions.path("partModel");
        ObjectList objects = objectApi.list(listContainerOptions);
        for(int i = 0; i < objects.size(); ++i){
            System.out.println(objects.get(i).getName());
            objectApi.delete(objects.get(i).getName());
        }
    }

    public static URI getObjectViaCDNsslUri(){
        /*
        URI uri = cdnContainer.getUri();
        URI sslUri = cdnContainer.getSslUri();
        URI streamingUri = cdnContainer.getStreamingUri();
        URI iosUri = cdnContainer.getIosUri();
        */
        return cdnContainer.getSslUri();
    }
}
