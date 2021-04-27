package org.ga4gh.starterkit.passport.utils;

public class SerializeView {

    public static class Always {}

    public static class User extends Always {}

    public static class UserRelational extends User {}

    public static class Visa extends Always {}

    public static class VisaRelational extends Visa {}

    public static class Never {}
}
