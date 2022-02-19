package com.uraneptus.sullysmod.client.renderer.entities;

import com.uraneptus.sullysmod.SullysMod;
import com.uraneptus.sullysmod.client.renderer.entities.model.CopperGolemModel;
import com.uraneptus.sullysmod.common.entities.CopperGolemEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class CopperGolemRenderer extends MobRenderer<CopperGolemEntity, CopperGolemModel<CopperGolemEntity>> {

    public CopperGolemRenderer(EntityRendererProvider.Context context) {
        super(context, new CopperGolemModel<>(context.bakeLayer(CopperGolemModel.LAYER_LOCATION)), 0.5F);
    }

    @Override
    public ResourceLocation getTextureLocation(CopperGolemEntity pEntity) {
        int id = pEntity.getEntityData().get(CopperGolemEntity.OXIDIZATION);
        return new ResourceLocation(SullysMod.MOD_ID, "textures/entity/copper_golem/copper_golem_" + id + ".png");
    }
}
